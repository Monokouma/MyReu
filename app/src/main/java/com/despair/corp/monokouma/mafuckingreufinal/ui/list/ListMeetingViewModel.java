package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Meeting;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter.MeetingViewStateHourFilterItem;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.meeting_items.MeetingsViewStateItem;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.room_filter.MeetingViewStateRoomFilterItem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListMeetingViewModel extends ViewModel {

    private static final int START_OF_HOUR_FILTER = 6;
    private static final int END_OF_HOUR_FILTER = 22;
    private static final int STEP_OF_HOUR_FILTER = 1;

    private final MediatorLiveData<ListMeetingViewState> listMeetingViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Map<Room, Boolean>> selectedRoomsLiveData = new MutableLiveData<>(populateMapWithAvailableRooms());

    // Filter : Hour
    private final MutableLiveData<Map<LocalTime, Boolean>> selectedHoursLiveData = new MutableLiveData<>(populateMapWithAvailableHours());

    private final Resources resources;

    private final MeetingRepository meetingRepository;

    public ListMeetingViewModel(MeetingRepository meetingRepository, Resources resources) {
        this.resources = resources;
        this.meetingRepository = meetingRepository;

        final LiveData<List<Meeting>> meetingLiveData = meetingRepository.getMeetingListMutableLiveData();
        listMeetingViewStateMediatorLiveData.addSource(meetingLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                listMeetingViewStateMediatorLiveData.setValue(
                        combine(meetings,
                                selectedRoomsLiveData.getValue(),
                                selectedHoursLiveData.getValue())
                );
            }
        });

        listMeetingViewStateMediatorLiveData.addSource(selectedRoomsLiveData, new Observer<Map<Room, Boolean>>() {
            @Override
            public void onChanged(Map<Room, Boolean> roomBooleanMap) {
                listMeetingViewStateMediatorLiveData.setValue(
                        combine(meetingLiveData.getValue(),
                                roomBooleanMap,
                                selectedHoursLiveData.getValue())
                );
            }
        });

        listMeetingViewStateMediatorLiveData.addSource(selectedHoursLiveData, new Observer<Map<LocalTime, Boolean>>() {
            @Override
            public void onChanged(Map<LocalTime, Boolean> localTimeBooleanMap) {
                listMeetingViewStateMediatorLiveData.setValue(
                        combine(meetingLiveData.getValue(),
                                selectedRoomsLiveData.getValue(),
                                localTimeBooleanMap)
                );
            }
        });
    }

    private ListMeetingViewState combine(@NonNull List<Meeting> meetingList,
                         @Nullable final Map<Room, Boolean> selectedRooms,
                         @Nullable final Map<LocalTime,Boolean> selectedHours) {

        if (selectedRooms == null || selectedHours == null) {
            throw new IllegalStateException("All internal LiveData must be initialized !");
        }

        List<Meeting> filteredMeetings = getFilteredMeetings(meetingList, selectedRooms, selectedHours);

        List<MeetingsViewStateItem> meetingViewStateItems = new ArrayList<>();

        for (Meeting meeting : filteredMeetings) {
            meetingViewStateItems.add(new MeetingsViewStateItem(
                    meeting.getMeetingId(),
                    meeting.getMeetingRoom().getDrawableResIcon(),
                    resources.getString(R.string.meeting_title, meeting.getMeetingName(), DateTimeFormatter.ofPattern("HH:mm").format(meeting.getMeetingTime()), resources.getString(meeting.getMeetingRoom().getStringResName())),
                    stringifyParticipants(meeting.getMeetingParticipantsList()),
                    meeting.getMeetingRoom().getColorRes()
            ));
        }

        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = getMeetingViewStateRoomFilterItems(selectedRooms);

        List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems = getMeetingViewStateHourFilterItems(selectedHours);

        return new ListMeetingViewState(
                meetingViewStateItems,
                meetingViewStateRoomFilterItems,
                meetingViewStateHourFilterItems
        );

    }

    @NonNull
    private Map<LocalTime, Boolean> populateMapWithAvailableHours() {
        Map<LocalTime, Boolean> hours = new LinkedHashMap<>();

        for (int hour = START_OF_HOUR_FILTER; hour <= END_OF_HOUR_FILTER; hour += STEP_OF_HOUR_FILTER) {
            hours.put(LocalTime.of(hour, 0), false);
        }

        return hours;
    }

    @NonNull
    private Map<Room, Boolean> populateMapWithAvailableRooms() {
        Map<Room, Boolean> rooms = new LinkedHashMap<>();

        for (Room room : Room.values()) {
            rooms.put(room, false);
        }

        return rooms;
    }

    @NonNull
    private String stringifyParticipants(@NonNull List<String> participants) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < participants.size(); i++) {
            String participant = participants.get(i);
            result.append(participant);
            if (i + 1 < participants.size()) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    @NonNull
    private List<MeetingViewStateRoomFilterItem> getMeetingViewStateRoomFilterItems(@NonNull Map<Room, Boolean> selectedRooms) {
        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = new ArrayList<>();
        for (Map.Entry<Room, Boolean> entry : selectedRooms.entrySet()) {
            Room room = entry.getKey();
            Boolean isRoomSelected = entry.getValue();

            int textColorInt = resources.getColor(R.color.black);

            meetingViewStateRoomFilterItems.add(
                    new MeetingViewStateRoomFilterItem(
                            room,
                            textColorInt,
                            isRoomSelected
                    )
            );
        }
        return meetingViewStateRoomFilterItems;
    }

    @NonNull
    private List<Meeting> getFilteredMeetings(@Nullable List<Meeting> meetings, @NonNull Map<Room, Boolean> selectedRooms, @NonNull Map<LocalTime, Boolean> selectedHours) {
        List<Meeting> filteredMeetings = new ArrayList<>();

        if (meetings == null) {
            return filteredMeetings;
        }

        for (Meeting meeting : meetings) {

            boolean atLeastOneRoomIsSelected = false;
            boolean meetingRoomMatches = false;
            boolean atLeastOneHourIsSelected = false;
            boolean meetingHourMatches = false;

            for (Map.Entry<Room, Boolean> roomEntry : selectedRooms.entrySet()) {
                Room room = roomEntry.getKey();
                boolean isRoomSelected = roomEntry.getValue();

                if (isRoomSelected) {
                    atLeastOneRoomIsSelected = true;
                }

                if (room == meeting.getMeetingRoom()) {
                    meetingRoomMatches = isRoomSelected;
                }
            }

            for (Map.Entry<LocalTime, Boolean> hourEntry : selectedHours.entrySet()) {
                LocalTime time = hourEntry.getKey();
                boolean isTimeSelected = hourEntry.getValue();

                if (isTimeSelected) {
                    atLeastOneHourIsSelected = true;
                }

                if (meeting.getMeetingTime().equals(time)
                        || (meeting.getMeetingTime().isAfter(time) && meeting.getMeetingTime().isBefore(time.plusHours(STEP_OF_HOUR_FILTER)))) {
                    meetingHourMatches = isTimeSelected;
                }
            }

            if (!atLeastOneRoomIsSelected) {
                meetingRoomMatches = true;
            }

            if (!atLeastOneHourIsSelected) {
                meetingHourMatches = true;
            }

            if (meetingRoomMatches && meetingHourMatches) {
                filteredMeetings.add(meeting);
            }
        }

        return filteredMeetings;
    }

    @NonNull
    private List<MeetingViewStateHourFilterItem> getMeetingViewStateHourFilterItems(@NonNull Map<LocalTime, Boolean> selectedHours) {
        List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems = new ArrayList<>();

        for (Map.Entry<LocalTime, Boolean> entry : selectedHours.entrySet()) {
            LocalTime localTime = entry.getKey();
            Boolean isHourSelected = entry.getValue();

            @DrawableRes
            int drawableResBackground;

            @ColorRes
            int textColorRes;

            if (isHourSelected) {
                textColorRes = R.color.white;
                drawableResBackground = R.color.pekin_color;
            } else {
                drawableResBackground = R.color.lavender_web;
                textColorRes = R.color.black;
            }

            meetingViewStateHourFilterItems.add(
                    new MeetingViewStateHourFilterItem(
                            localTime,
                            localTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                            drawableResBackground,
                            textColorRes
                    )
            );

        }
        return meetingViewStateHourFilterItems;
    }


    public LiveData<ListMeetingViewState> getListMeetingViewStateMediatorLiveData() {
        return listMeetingViewStateMediatorLiveData;
    }

    public void onRoomSelected(Room room) {
        Map<Room, Boolean> selectedRooms = selectedRoomsLiveData.getValue();
        if (selectedRooms == null) {
            return;
        }
        for (Map.Entry<Room, Boolean> entry : selectedRooms.entrySet()) {
            if (entry.getKey() == room) {
                entry.setValue(!entry.getValue());
            }
        }
        selectedRoomsLiveData.setValue(selectedRooms);
    }

    public void onHourSelected(LocalTime hour) {
        Map<LocalTime, Boolean> selectedHours = selectedHoursLiveData.getValue();

        if (selectedHours == null) {
            return;
        }

        for (Map.Entry<LocalTime, Boolean> entry : selectedHours.entrySet()) {
            if (entry.getKey().equals(hour)) {
                entry.setValue(!entry.getValue());
                break;
            }
        }

        selectedHoursLiveData.setValue(selectedHours);
    }

    public void onDeleteButtonClick(int meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }
}
