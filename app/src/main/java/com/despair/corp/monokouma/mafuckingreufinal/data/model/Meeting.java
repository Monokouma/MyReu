package com.despair.corp.monokouma.mafuckingreufinal.data.model;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Meeting {
    private final int meetingId;

    @NonNull
    private final String meetingName;

    @NonNull
    private final LocalTime meetingTime;

    @NonNull
    private final List<String> meetingParticipantsList;

    @NonNull
    private final Room meetingRoom;

    public Meeting(
            int meetingId,
            @NonNull String meetingName,
            @NonNull LocalTime meetingTime,
            @NonNull List<String> meetingParticipantsList,
            @NonNull Room meetingRoom
    ) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingTime = meetingTime;
        this.meetingParticipantsList = meetingParticipantsList;
        this.meetingRoom = meetingRoom;
    }

    public int getMeetingId() {
        return meetingId;
    }

    @NonNull
    public String getMeetingName() {
        return meetingName;
    }

    @NonNull
    public LocalTime getMeetingTime() {
        return meetingTime;
    }

    @NonNull
    public List<String> getMeetingParticipantsList() {
        return meetingParticipantsList;
    }

    @NonNull
    public Room getMeetingRoom() {
        return meetingRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return meetingId == meeting.meetingId && meetingName.equals(meeting.meetingName) && meetingTime.equals(meeting.meetingTime) && meetingParticipantsList.equals(meeting.meetingParticipantsList) && meetingRoom == meeting.meetingRoom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, meetingName, meetingTime, meetingParticipantsList, meetingRoom);
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting{" +
                "meetingId=" + meetingId +
                ", meetingName='" + meetingName + '\'' +
                ", meetingTime=" + meetingTime +
                ", meetingParticipantsList=" + meetingParticipantsList +
                ", meetingRoom=" + meetingRoom +
                '}';
    }
}
