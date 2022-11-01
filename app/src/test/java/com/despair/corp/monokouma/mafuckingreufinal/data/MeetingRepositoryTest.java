package com.despair.corp.monokouma.mafuckingreufinal.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.despair.corp.monokouma.mafuckingreufinal.LiveDataTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Meeting;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingRepository meetingRepository;

    @Before
    public void setUp() {
        meetingRepository = new MeetingRepository();
    }

    @Test
    public void add_meeting() {
        //Given
        String meetingName = "foo";
        LocalTime meetingHour = LocalTime.of(12, 21);
        List<String> participants = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            participants.add("Participant"+ i);
        }
        Room room = Room.Chicago;

        //When
        meetingRepository.saveMeeting(
                meetingName,
                meetingHour,
                participants,
                room
        );
        List<Meeting> results = LiveDataTestUtils.getValueForTesting(meetingRepository.getMeetingListMutableLiveData());

        //Then
        assertEquals(1, results.size());
        Meeting result = results.get(0);
        assertEquals(0, result.getMeetingId());
        assertEquals(meetingName, result.getMeetingName());
        assertEquals(meetingHour, result.getMeetingTime());
        assertEquals(participants, result.getMeetingParticipantsList());
        assertEquals(room, result.getMeetingRoom());
    }

    @Test
    public void add_two_meeting() {
        //Given
        String meetingName = "foo";
        LocalTime meetingHour = LocalTime.of(12, 21);
        List<String> participants = new ArrayList<String>(3);
        for (int i = 0; i < 3; i++) {
            participants.add("Participant"+ i);
        }
        Room room = Room.Chicago;

        String meetingName1 = "foo1";
        LocalTime meetingHour1 = LocalTime.of(13, 21);
        List<String> participants1 = new ArrayList<String>(3);
        for (int i = 0; i < 3; i++) {
            participants1.add("Participant1"+ i);
        }
        Room room1 = Room.Berlin;

        //When
        meetingRepository.saveMeeting(
                meetingName,
                meetingHour,
                participants,
                room
        );

        meetingRepository.saveMeeting(
                meetingName1,
                meetingHour1,
                participants1,
                room1
        );


        List<Meeting> results = LiveDataTestUtils.getValueForTesting(meetingRepository.getMeetingListMutableLiveData());

        //Then
        assertEquals(2, results.size());
        Meeting result = results.get(0);
        assertEquals(0, result.getMeetingId());
        assertEquals(meetingName, result.getMeetingName());
        assertEquals(meetingHour, result.getMeetingTime());
        assertEquals(participants, result.getMeetingParticipantsList());
        assertEquals(room, result.getMeetingRoom());

        Meeting result1 = results.get(1);
        assertEquals(1, result1.getMeetingId());
        assertEquals(meetingName1, result1.getMeetingName());
        assertEquals(meetingHour1, result1.getMeetingTime());
        assertEquals(participants1, result1.getMeetingParticipantsList());
        assertEquals(room1, result1.getMeetingRoom());
    }

    @Test
    public void remove_one_meeting() {
        //Given
        String meetingName = "foo";
        LocalTime meetingHour = LocalTime.of(12, 21);
        List<String> participants = new ArrayList<String>(3);
        for (int i = 0; i < 3; i++) {
            participants.add("Participant"+ i);
        }
        Room room = Room.Chicago;

        //When
        meetingRepository.saveMeeting(
                meetingName,
                meetingHour,
                participants,
                room
        );
        meetingRepository.deleteMeeting(0);
        List<Meeting> results = LiveDataTestUtils.getValueForTesting(meetingRepository.getMeetingListMutableLiveData());

        //Then
        assertEquals(0, results.size());
    }

    @Test
    public void give_non_existent_id_should_return_null() {
        //Given
        String meetingName = "foo";
        LocalTime meetingHour = LocalTime.of(12, 21);
        List<String> participants = new ArrayList<String>(3);
        for (int i = 0; i < 3; i++) {
            participants.add("Participant"+ i);
        }
        Room room = Room.Chicago;

        String meetingName1 = "foo1";
        LocalTime meetingHour1 = LocalTime.of(13, 21);
        List<String> participants1 = new ArrayList<String>(3);
        for (int i = 0; i < 3; i++) {
            participants1.add("Participant1"+ i);
        }
        Room room1 = Room.Berlin;

        //When
        meetingRepository.saveMeeting(
                meetingName,
                meetingHour,
                participants,
                room
        );

        meetingRepository.saveMeeting(
                meetingName1,
                meetingHour1,
                participants1,
                room1
        );


        Meeting result = LiveDataTestUtils.getValueForTesting(meetingRepository.getMeetingsByIdLiveData(666));
        assertNull(result);
    }

    @Test
    public void shouldRemoveMeetingEvenIfListIsEmpty() {
        // When
        meetingRepository.deleteMeeting(0);
        List<Meeting> results = LiveDataTestUtils.getValueForTesting(meetingRepository.getMeetingListMutableLiveData());

        // Then
        assertEquals(0, results.size());
    }





}
