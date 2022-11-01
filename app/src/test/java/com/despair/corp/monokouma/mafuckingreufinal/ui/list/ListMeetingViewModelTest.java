package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import static org.junit.Assert.assertEquals;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.despair.corp.monokouma.mafuckingreufinal.LiveDataTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Meeting;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter.MeetingViewStateHourFilterItem;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.meeting_items.MeetingsViewStateItem;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.room_filter.MeetingViewStateRoomFilterItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ListMeetingViewModelTest {
    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingRepository meetingRepository;
    private Resources resources;

    private final MutableLiveData<List<Meeting>> listMeetingsLiveData = new MutableLiveData<>();

    private ListMeetingViewModel listMeetingViewModel;

    @Before
    public void setUp() {
        meetingRepository = Mockito.mock(MeetingRepository.class);
        resources = Mockito.mock(Resources.class);

        List<Meeting> meetings = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            meetings.add(
                    new Meeting(
                            i,
                            "name" + i,
                            LocalTime.of(i + 6, 0),
                            new ArrayList<>(),
                            Room.values()[i]
                    )
            );
        }
        listMeetingsLiveData.setValue(meetings);

        Mockito.doReturn("Paris").when(resources).getString(R.string.paris);
        Mockito.doReturn("title0").when(resources).getString(R.string.meeting_title, "name0", "06:00", "Paris");

        Mockito.doReturn("Tokyo").when(resources).getString(R.string.tokyo);
        Mockito.doReturn("title1").when(resources).getString(R.string.meeting_title, "name1", "07:00", "Tokyo");

        Mockito.doReturn("New_York").when(resources).getString(R.string.new_york);
        Mockito.doReturn("title2").when(resources).getString(R.string.meeting_title, "name2", "08:00", "New_York");

        Mockito.doReturn(listMeetingsLiveData).when(meetingRepository).getMeetingListMutableLiveData();

        listMeetingViewModel = new ListMeetingViewModel(meetingRepository, resources);
    }

    @Test
    public void nominal_case() {
        // When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        // Then
        List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            meetingsViewStateItems.add(getMeetingsViewStateItem(i));
        }

        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            meetingViewStateRoomFilterItems.add(getMeetingViewStateRoomFilterItem(i));
        }

        List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems = new ArrayList<>(17);
        for (int i = 0; i < 17; i++) {
            meetingViewStateHourFilterItems.add(getMeetingViewStateHourFilterItem(i));
        }

        assertEquals(
                new ListMeetingViewState(
                        meetingsViewStateItems,
                        meetingViewStateRoomFilterItems,
                        meetingViewStateHourFilterItems
                ),
                result
        );
    }

    @Test
    public void remove_meeting_from_list() {
        // When
        listMeetingViewModel.onDeleteButtonClick(1);

        // Then
        Mockito.verify(meetingRepository).deleteMeeting(1);
    }

    @Test
    public void one_room_selected_should_return_one_meeting() {
        //Given
        listMeetingViewModel.onRoomSelected(Room.Paris);

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>(3);
        meetingsViewStateItems.add(getMeetingsViewStateItem(0));

        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            meetingViewStateRoomFilterItems.add(getMeetingViewStateRoomFilterItem(i));
        }
        meetingViewStateRoomFilterItems.remove(getMeetingViewStateRoomFilterItem(0));
        meetingViewStateRoomFilterItems.add(0, new MeetingViewStateRoomFilterItem(
                Room.values()[0],
                resources.getColor(Room.values()[0].getColorRes()),
                true
        ));

        List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems = new ArrayList<>(17);
        for (int i = 0; i < 17; i++) {
            meetingViewStateHourFilterItems.add(getMeetingViewStateHourFilterItem(i));
        }

        assertEquals(
                new ListMeetingViewState(
                        meetingsViewStateItems,
                        meetingViewStateRoomFilterItems,
                        meetingViewStateHourFilterItems
                ),
                result
        );
    }

    @Test
    public void two_room_selected_should_return_two_meetings() {
        //Given
        listMeetingViewModel.onRoomSelected(Room.Paris);
        listMeetingViewModel.onRoomSelected(Room.Tokyo);

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>(3);
        meetingsViewStateItems.add(getMeetingsViewStateItem(0));
        meetingsViewStateItems.add(getMeetingsViewStateItem(1));

        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            meetingViewStateRoomFilterItems.add(getMeetingViewStateRoomFilterItem(i));
        }
        meetingViewStateRoomFilterItems.remove(getMeetingViewStateRoomFilterItem(0));
        meetingViewStateRoomFilterItems.remove(getMeetingViewStateRoomFilterItem(1));
        meetingViewStateRoomFilterItems.add(0, new MeetingViewStateRoomFilterItem(
                Room.values()[0],
                resources.getColor(Room.values()[0].getColorRes()),
                true
        ));
        meetingViewStateRoomFilterItems.add(1, new MeetingViewStateRoomFilterItem(
                Room.values()[1],
                resources.getColor(Room.values()[1].getColorRes()),
                true
        ));

        List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems = new ArrayList<>(17);
        for (int i = 0; i < 17; i++) {
            meetingViewStateHourFilterItems.add(getMeetingViewStateHourFilterItem(i));
        }

        assertEquals(
                new ListMeetingViewState(
                        meetingsViewStateItems,
                        meetingViewStateRoomFilterItems,
                        meetingViewStateHourFilterItems
                ),
                result
        );
    }

    @Test
    public void two_room_selected_should_return_one_meeting() {
        //Given
        listMeetingViewModel.onRoomSelected(Room.Paris);
        listMeetingViewModel.onRoomSelected(Room.Berlin);

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>(3);
        meetingsViewStateItems.add(getMeetingsViewStateItem(0));

        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            meetingViewStateRoomFilterItems.add(getMeetingViewStateRoomFilterItem(i));
        }
        meetingViewStateRoomFilterItems.remove(getMeetingViewStateRoomFilterItem(0));
        meetingViewStateRoomFilterItems.remove(getMeetingViewStateRoomFilterItem(6));
        meetingViewStateRoomFilterItems.add(0, new MeetingViewStateRoomFilterItem(
                Room.values()[0],
                resources.getColor(Room.values()[0].getColorRes()),
                true
        ));
        meetingViewStateRoomFilterItems.add(6, new MeetingViewStateRoomFilterItem(
                Room.values()[6],
                resources.getColor(Room.values()[6].getColorRes()),
                true
        ));

        List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems = new ArrayList<>(17);
        for (int i = 0; i < 17; i++) {
            meetingViewStateHourFilterItems.add(getMeetingViewStateHourFilterItem(i));
        }

        assertEquals(
                new ListMeetingViewState(
                        meetingsViewStateItems,
                        meetingViewStateRoomFilterItems,
                        meetingViewStateHourFilterItems
                ),
                result
        );
    }

    @Test
    public void one_room_selected_should_return_zero_meeting() {
        //Given
        listMeetingViewModel.onRoomSelected(Room.Moscou);

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> results = result.getMeetingsViewStateItem();
        assertEquals(0, results.size());

    }

    @Test
    public void one_hour_is_selected_should_return_one_meeting() {
        //Given
        listMeetingViewModel.onHourSelected(LocalTime.of(6, 0));

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> results = result.getMeetingsViewStateItem();
        assertEquals(1, results.size());
    }

    @Test
    public void two_hour_is_selected_should_return_two_meeting() {
        //Given
        listMeetingViewModel.onHourSelected(LocalTime.of(6, 0));
        listMeetingViewModel.onHourSelected(LocalTime.of(8, 0));

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> results = result.getMeetingsViewStateItem();
        assertEquals(2, results.size());
    }

    @Test
    public void one_hour_is_selected_should_return_zero_meeting() {
        //Given
        listMeetingViewModel.onHourSelected(LocalTime.of(10, 0));

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> results = result.getMeetingsViewStateItem();
        assertEquals(0, results.size());
    }

    @Test
    public void two_hour_is_selected_should_return_one_meeting() {
        //Given
        listMeetingViewModel.onHourSelected(LocalTime.of(7, 0));
        listMeetingViewModel.onHourSelected(LocalTime.of(10, 0));

        //When
        ListMeetingViewState result = LiveDataTestUtils.getValueForTesting(listMeetingViewModel.getListMeetingViewStateMediatorLiveData());

        //Then
        List<MeetingsViewStateItem> results = result.getMeetingsViewStateItem();
        assertEquals(1, results.size());
    }

    // region IN
    @NonNull
    private MeetingsViewStateItem getMeetingsViewStateItem(int i) {
        return new MeetingsViewStateItem(
                i,
                Room.values()[i].getDrawableResIcon(),
                "title" + i,
                "",
                Room.values()[i].getColorRes()
        );
    }

    @NonNull
    private MeetingViewStateRoomFilterItem getMeetingViewStateRoomFilterItem(int j) {
        return new MeetingViewStateRoomFilterItem(
                Room.values()[j],
                resources.getColor(Room.values()[j].getColorRes()),
                false
        );
    }

    @NonNull
    private MeetingViewStateHourFilterItem getMeetingViewStateHourFilterItem(int i) {
        return new MeetingViewStateHourFilterItem(
                LocalTime.of(i + 6, 0),
                LocalTime.of(i + 6, 0).toString(),
                R.color.lavender_web,
                R.color.black
        );
    }
    // endregion IN
}
