package com.despair.corp.monokouma.mafuckingreufinal.ui.create;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.despair.corp.monokouma.mafuckingreufinal.LiveDataTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateMeetingViewModelTest {


    private static final String EXPECTED_MEETING_NAME_ERROR = "EXPECTED_MEETING_NAME_ERROR";
    private static final String EXPECTED_MEETING_PARTICIPANTS_ERROR = "EXPECTED_MEETING_PARTICIPANTS_ERROR";
    private static final String EXPECTED_MEETING_ROOM_ERROR = "EXPECTED_MEETING_ROOM_ERROR";
    private static final String EXPECTED_DEFAULT_TIME = LocalTime.now().plusMinutes(30L).format(DateTimeFormatter.ofPattern("HH:mm"));
    private static final String DEFAULT_TIME_MESSAGE = "DEFAULT_TIME_MESSAGE";
    private static final String EXPECTED_TIME = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingRepository meetingRepository;

    private CreateMeetingViewModel createMeetingViewModel;


    @Before
    public void setUp() {
        Resources resources = Mockito.mock(Resources.class);
        meetingRepository = Mockito.mock(MeetingRepository.class);

        Mockito.doReturn(EXPECTED_MEETING_NAME_ERROR).when(resources).getString(R.string.error_meeting_name);
        Mockito.doReturn(EXPECTED_MEETING_PARTICIPANTS_ERROR).when(resources).getString(R.string.error_participants);
        Mockito.doReturn(EXPECTED_MEETING_ROOM_ERROR).when(resources).getString(R.string.error_room);
        Mockito.doReturn(DEFAULT_TIME_MESSAGE).when(resources).getString(R.string.meeting_hour);

        createMeetingViewModel = new CreateMeetingViewModel(meetingRepository, resources);
    }

    @Test
    public void nominal_case() {
        //Given
        CreateMeetingViewState result = LiveDataTestUtils.getValueForTesting(createMeetingViewModel.getCreateMeetingViewStateMutableLiveData());

        //Then
        assertArrayEquals(Room.values(), result.getMeetingRooms());
        assertEquals(DEFAULT_TIME_MESSAGE + " " + EXPECTED_DEFAULT_TIME, result.getMeetingTime());
        assertNull(result.getMeetingNameError());
        assertNull(result.getMeetingParticipantsError());
        assertNull(result.getMeetingRoomError());
    }

    @Test
    public void empty_name_is_given_should_return_name_error() {
        //Given
        createMeetingViewModel.onMeetingNameTextChanged("");
        createMeetingViewModel.onMeetingParticipantTextChanged("foo");
        createMeetingViewModel.onMeetingRoomItemClicked(Room.Chicago);
        createMeetingViewModel.onMeetingScheduleChanged(LocalTime.now());
        createMeetingViewModel.createMeetingButtonClicked();

        CreateMeetingViewState result = LiveDataTestUtils.getValueForTesting(createMeetingViewModel.getCreateMeetingViewStateMutableLiveData());

        //Then
        assertArrayEquals(Room.values(), result.getMeetingRooms());
        assertEquals(result.getMeetingNameError(), EXPECTED_MEETING_NAME_ERROR);
        assertEquals(DEFAULT_TIME_MESSAGE + " " + EXPECTED_TIME, result.getMeetingTime());
        assertNull(result.getMeetingParticipantsError());
        assertNull(result.getMeetingRoomError());

    }

    @Test
    public void empty_participants_is_given_should_return_participants_error() {
        //Given
        createMeetingViewModel.onMeetingNameTextChanged("foo");
        createMeetingViewModel.onMeetingParticipantTextChanged("");
        createMeetingViewModel.onMeetingRoomItemClicked(Room.Chicago);
        createMeetingViewModel.onMeetingScheduleChanged(LocalTime.now());
        createMeetingViewModel.createMeetingButtonClicked();

        CreateMeetingViewState result = LiveDataTestUtils.getValueForTesting(createMeetingViewModel.getCreateMeetingViewStateMutableLiveData());


        //Then
        assertArrayEquals(Room.values(), result.getMeetingRooms());
        assertNull(result.getMeetingNameError());
        assertEquals(DEFAULT_TIME_MESSAGE + " " + EXPECTED_TIME, result.getMeetingTime());
        assertEquals(result.getMeetingParticipantsError(), EXPECTED_MEETING_PARTICIPANTS_ERROR);
        assertNull(result.getMeetingRoomError());

    }

    @Test
    public void empty_room_is_given_should_return_room_error() {
        //Given
        createMeetingViewModel.onMeetingNameTextChanged("foo");
        createMeetingViewModel.onMeetingParticipantTextChanged("j");
        createMeetingViewModel.onMeetingScheduleChanged(LocalTime.now());
        createMeetingViewModel.createMeetingButtonClicked();

        CreateMeetingViewState result = LiveDataTestUtils.getValueForTesting(createMeetingViewModel.getCreateMeetingViewStateMutableLiveData());

        //Then
        assertArrayEquals(Room.values(), result.getMeetingRooms());
        assertNull(result.getMeetingNameError());
        assertEquals(DEFAULT_TIME_MESSAGE + " " + EXPECTED_TIME, result.getMeetingTime());
        assertNull(result.getMeetingParticipantsError());
        assertEquals(result.getMeetingRoomError(), EXPECTED_MEETING_ROOM_ERROR);
    }

    @Test
    public void empty_hour_is_given_should_return_actual_hour() {
        //Given
        createMeetingViewModel.onMeetingNameTextChanged("foo");
        createMeetingViewModel.onMeetingParticipantTextChanged("d");
        createMeetingViewModel.onMeetingRoomItemClicked(Room.Chicago);
        createMeetingViewModel.createMeetingButtonClicked();

        CreateMeetingViewState result = LiveDataTestUtils.getValueForTesting(createMeetingViewModel.getCreateMeetingViewStateMutableLiveData());

        //Then
        assertArrayEquals(Room.values(), result.getMeetingRooms());
        assertNull(result.getMeetingNameError());
        assertEquals(DEFAULT_TIME_MESSAGE + " " + EXPECTED_DEFAULT_TIME, result.getMeetingTime());
        assertNull(result.getMeetingParticipantsError());
        assertNull(result.getMeetingRoomError());
    }
}
