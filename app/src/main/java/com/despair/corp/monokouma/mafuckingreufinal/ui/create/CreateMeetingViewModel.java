package com.despair.corp.monokouma.mafuckingreufinal.ui.create;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;
import com.despair.corp.monokouma.mafuckingreufinal.utils.SingleLiveEvent;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class CreateMeetingViewModel extends ViewModel {

    private String meetingName;
    private List<String> meetingParticipants = new ArrayList<>();
    private LocalTime meetingSchedule;
    private Room meetingRoom;

    private final Resources resources;

    private final MeetingRepository meetingRepository;

    private final MutableLiveData<CreateMeetingViewState> createMeetingViewStateMutableLiveData = new MutableLiveData<>();

    private final SingleLiveEvent<Void> onActivityFinishEvent = new SingleLiveEvent<>();

    public CreateMeetingViewModel(MeetingRepository meetingRepository, Resources resources) {
        this.resources = resources;
        this.meetingRepository = meetingRepository;

        String defaultSchedule = new StringBuilder()
                .append(resources.getString(R.string.meeting_hour))
                .append(" ")
                .append(LocalTime.now().plusMinutes(30L).format(DateTimeFormatter.ofPattern("HH:mm")))
                .toString();

        createMeetingViewStateMutableLiveData.setValue(
                new CreateMeetingViewState(
                        null,
                        null,
                        null,
                        Room.values(),
                        defaultSchedule
                )
        );
    }


    public void onMeetingNameTextChanged(String inputMeetingName) {
        this.meetingName = inputMeetingName;
    }

    public void onMeetingParticipantTextChanged(String inputMeetingParticipants) {
        meetingParticipants.clear();

        String[] participantsList = inputMeetingParticipants.split("[,; \n]");


        for (String participant : participantsList) {
            String participantCleaned = participant.trim();

            if (!participantCleaned.isEmpty()) {
                meetingParticipants.add(participantCleaned);
            }
        }
    }

    public void onMeetingRoomItemClicked(Room inputMeetingRoom) {
        this.meetingRoom = inputMeetingRoom;
    }

    public void onMeetingScheduleChanged(LocalTime inputMeetingSchedule) {
        this.meetingSchedule = inputMeetingSchedule;
        String stringBuilder = new StringBuilder()
                .append(resources.getString(R.string.meeting_hour))
                .append(" ")
                .append(inputMeetingSchedule.format(DateTimeFormatter.ofPattern("HH:mm"))).toString();
        createMeetingViewStateMutableLiveData.setValue(
                new CreateMeetingViewState(
                null,
                null,
                null,
                        Room.values(),
                        stringBuilder
                )
        );
    }

    public void createMeetingButtonClicked() {
        //harvest
        checkIncorrectInput(meetingName, meetingParticipants, meetingSchedule, meetingRoom);
    }

    private void checkIncorrectInput(String meetingName, List<String> meetingParticipantsList, LocalTime meetingSchedule, Room meetingRoom) {
        String meetingNameError;
        String meetingParticipantsError;
        String meetingRoomError;
        LocalTime defaultLocalTime;

        if (meetingName == null || meetingName.isEmpty()) {
            meetingNameError = resources.getString(R.string.error_meeting_name);
        } else {
            meetingNameError = null;
        }

        if (meetingParticipantsList == null || meetingParticipantsList.isEmpty()) {
            meetingParticipantsError = resources.getString(R.string.error_participants);
        } else {
            meetingParticipantsError = null;
        }

        if (meetingRoom == null) {
            meetingRoomError = resources.getString(R.string.error_room);
        } else {
            meetingRoomError = null;
        }

        if (meetingSchedule == null) {
            defaultLocalTime = LocalTime.now().plusMinutes(30L);
        } else {
            defaultLocalTime = meetingSchedule;
        }

        if (meetingNameError == null && meetingParticipantsError == null && meetingRoomError == null) {
            //create meeting in repo
            meetingRepository.saveMeeting(meetingName, defaultLocalTime, meetingParticipantsList, meetingRoom);
            onActivityFinishEvent.call();
        }

        String stringBuilder = new StringBuilder()
                .append(resources.getString(R.string.meeting_hour))
                .append(" ")
                .append(defaultLocalTime.format(DateTimeFormatter.ofPattern("HH:mm"))).toString();

        createMeetingViewStateMutableLiveData.setValue(
                new CreateMeetingViewState(
                        meetingNameError,
                        meetingParticipantsError,
                        meetingRoomError,
                        Room.values(),
                        stringBuilder
                )
        );

    }

    public LiveData<CreateMeetingViewState> getCreateMeetingViewStateMutableLiveData() {
        return createMeetingViewStateMutableLiveData;
    }

    public SingleLiveEvent<Void> getOnActivityFinishEvent() {
        return onActivityFinishEvent;
    }
}
