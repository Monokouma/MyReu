package com.despair.corp.monokouma.mafuckingreufinal.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.despair.corp.monokouma.mafuckingreufinal.data.model.Meeting;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingListMutableLiveData = new MutableLiveData<>();

    private int maxMeetingId = 0;

    public MeetingRepository() {

    }

    public void saveMeeting(
            @NonNull String meetingName,
            @NonNull LocalTime meetingSchedule,
            @NonNull List<String> meetingParticipantsList,
            @NonNull Room meetingRoom
    ) {
        List<Meeting> currentList = meetingListMutableLiveData.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        currentList.add(
                new Meeting(
                        maxMeetingId,
                        meetingName,
                        meetingSchedule,
                        meetingParticipantsList,
                        meetingRoom
                )
        );
        maxMeetingId++;
        meetingListMutableLiveData.setValue(currentList);
    }

    public LiveData<List<Meeting>> getMeetingListMutableLiveData() {
        return meetingListMutableLiveData;
    }

    public void deleteMeeting(int meetingId) {
        List<Meeting> currentList = meetingListMutableLiveData.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        for (Iterator<Meeting> iterator = currentList.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getMeetingId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        meetingListMutableLiveData.setValue(currentList);
    }

    public LiveData<Meeting> getMeetingsByIdLiveData(int meetingId) {
        return Transformations.map(meetingListMutableLiveData, new Function<List<Meeting>, Meeting>() {
            @Override
            public Meeting apply(List<Meeting> meetings) {
                for (Meeting meeting : meetings) {
                    if (meeting.getMeetingId() == meetingId) {
                        return meeting;
                    }
                }

                return null;
            }
        });
    }
}
