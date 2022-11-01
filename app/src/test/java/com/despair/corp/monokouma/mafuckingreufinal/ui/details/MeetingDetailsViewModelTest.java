package com.despair.corp.monokouma.mafuckingreufinal.ui.details;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import android.app.Application;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.despair.corp.monokouma.mafuckingreufinal.LiveDataTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.MainApplication;
import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Meeting;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;
import com.despair.corp.monokouma.mafuckingreufinal.ui.details.participant_adapter.ParticipantViewStateItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MeetingDetailsViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static final int EXPECTED_MEETING_ID = 5454;
    private static final String EXPECTED_MEETING_NAME = "EXPECTED_MEETING_NAME";
    private static final LocalTime EXPECTED_MEETING_HOUR = LocalTime.now();
    private static final String EXPECTED_MEETING_HOUR_NOW = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    private static final Room EXPECTED_MEETING_ROOM = Room.Pekin;
    private static final String EXPECTED_MEETING_ROOM_NAME = "Pekin";

    private Application application;

    private Resources resources;

    private MeetingRepository meetingRepository;

    private MutableLiveData<Meeting> meetingMutableLiveData = new MutableLiveData<>();

    private MeetingDetailsViewModel meetingDetailsViewModel;

    @Before
    public void setUp() {
        meetingRepository = Mockito.mock(MeetingRepository.class);
        resources = Mockito.mock(Resources.class);

        Mockito.doReturn(meetingMutableLiveData).when(meetingRepository).getMeetingsByIdLiveData(EXPECTED_MEETING_ID);
        Mockito.doReturn("meeting_running").when(resources).getString(R.string.meeting_running);
        Mockito.doReturn("meeting_start_at").when(resources).getString(eq(R.string.meeting_start_at), any());
        Mockito.doReturn("meeting_finished").when(resources).getString(R.string.meeting_finished);
        Mockito.when(resources.getString(EXPECTED_MEETING_ROOM.getStringResName())).thenReturn(EXPECTED_MEETING_ROOM_NAME);

        meetingMutableLiveData.setValue(getExpectedMeeting());

        meetingDetailsViewModel = new MeetingDetailsViewModel(meetingRepository, resources);
    }

    @Test
    public void nominal_case_15_minutes_in() {
        // When
        meetingDetailsViewModel.onDetailsLoad(EXPECTED_MEETING_ID);
        MeetingDetailsViewState result = LiveDataTestUtils.getValueForTesting(meetingDetailsViewModel.getMeetingDetailsViewStateMutableLiveData());

        // Then

        assertEquals(
            new MeetingDetailsViewState(
                EXPECTED_MEETING_ROOM.getColorRes(),
                EXPECTED_MEETING_ROOM.getDrawableResIcon(),
                EXPECTED_MEETING_NAME + " - " + EXPECTED_MEETING_HOUR_NOW + " - " + EXPECTED_MEETING_ROOM_NAME,
                getExpectedParticipantViewStateItems(),
                "meeting_running"
            ),
            result
        );
    }

    @Test
    public void nominal_case_15_minutes_before() {
        // Given
        LocalTime meetingHour = LocalTime.of(18, 20);
        meetingMutableLiveData.setValue(getExpectedMeeting(meetingHour));

        // When
        meetingDetailsViewModel.onDetailsLoad(EXPECTED_MEETING_ID);
        MeetingDetailsViewState result = LiveDataTestUtils.getValueForTesting(meetingDetailsViewModel.getMeetingDetailsViewStateMutableLiveData());

        // Then
        assertEquals(
            new MeetingDetailsViewState(
                EXPECTED_MEETING_ROOM.getColorRes(),
                EXPECTED_MEETING_ROOM.getDrawableResIcon(),
                EXPECTED_MEETING_NAME + " - " + meetingHour + " - " + EXPECTED_MEETING_ROOM_NAME,
                getExpectedParticipantViewStateItems(),
                "meeting_start_at"
            ),
            result
        );
    }

    @Test
    public void nominal_case_45_minutes_after_beginning() {
        // Given
        LocalTime meetingHour = LocalTime.now().minusMinutes(45);
        meetingMutableLiveData.setValue(getExpectedMeeting(meetingHour));

        // When
        meetingDetailsViewModel.onDetailsLoad(EXPECTED_MEETING_ID);
        MeetingDetailsViewState result = LiveDataTestUtils.getValueForTesting(meetingDetailsViewModel.getMeetingDetailsViewStateMutableLiveData());

        // Then
        assertEquals(
            new MeetingDetailsViewState(
                EXPECTED_MEETING_ROOM.getColorRes(),
                EXPECTED_MEETING_ROOM.getDrawableResIcon(),
                EXPECTED_MEETING_NAME + " - " + meetingHour.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + EXPECTED_MEETING_ROOM_NAME,
                getExpectedParticipantViewStateItems(),
                "meeting_finished"
            ),
            result
        );
    }

    // region IN
    @NonNull
    private Meeting getExpectedMeeting() {
        return getExpectedMeeting(EXPECTED_MEETING_HOUR);
    }

    @NonNull
    private Meeting getExpectedMeeting(@NonNull LocalTime expectedHour) {
        return new Meeting(
            EXPECTED_MEETING_ID,
            EXPECTED_MEETING_NAME,
            expectedHour,
            getExpectedMeetingParticipants(),
            EXPECTED_MEETING_ROOM
        );
    }

    private List<String> getExpectedMeetingParticipants() {
        List<String> result = new ArrayList<>(3);

        for (int i = 0; i < 3; i++) {
            result.add("Participant" + i);
        }

        return result;
    }
    // endregion IN

    // region OUT
    private List<ParticipantViewStateItem> getExpectedParticipantViewStateItems() {
        List<ParticipantViewStateItem> result = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            result.add(new ParticipantViewStateItem("Participant" + i + "@lamzone.fr"));
        }

        return result;
    }
    // endregion OUT
}
