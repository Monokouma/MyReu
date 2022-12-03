package com.despair.corp.monokouma.mafuckingreufinal.ui.create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;

import java.util.Arrays;
import java.util.Objects;

public class CreateMeetingViewState {

    @Nullable
    private final String meetingNameError;

    @Nullable
    private final String meetingParticipantsError;

    @Nullable
    private final String meetingRoomError;

    @Nullable
    private final Room[] meetingRooms;

    @Nullable
    private final String meetingTime;

    public CreateMeetingViewState(@Nullable String meetingNameError,
                                  @Nullable String meetingParticipantsError,
                                  @Nullable String meetingRoomError,
                                  @Nullable Room[] meetingRooms,
                                  @Nullable String meetingTime) {
        this.meetingNameError = meetingNameError;
        this.meetingParticipantsError = meetingParticipantsError;
        this.meetingRoomError = meetingRoomError;
        this.meetingRooms = meetingRooms;
        this.meetingTime = meetingTime;
    }

    @Nullable
    public String getMeetingNameError() {
        return meetingNameError;
    }

    @Nullable
    public String getMeetingParticipantsError() {
        return meetingParticipantsError;
    }

    @Nullable
    public String getMeetingRoomError() {
        return meetingRoomError;
    }

    @Nullable
    public Room[] getMeetingRooms() {
        return meetingRooms;
    }

    @Nullable
    public String getMeetingTime() {
        return meetingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateMeetingViewState that = (CreateMeetingViewState) o;
        return Objects.equals(meetingNameError, that.meetingNameError)
            && Objects.equals(meetingParticipantsError, that.meetingParticipantsError)
            && Objects.equals(meetingRoomError, that.meetingRoomError)
            && Arrays.equals(meetingRooms, that.meetingRooms)
            && meetingTime.equals(that.meetingTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(meetingNameError,
            meetingParticipantsError,
            meetingRoomError,
            meetingTime);
        result = 31 * result + Arrays.hashCode(meetingRooms);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateMeetingViewState{" +
                "meetingNameError='" + meetingNameError + '\'' +
                ", meetingParticipantsError='" + meetingParticipantsError + '\'' +
                ", meetingRoomError='" + meetingRoomError + '\'' +
                ", meetingRooms=" + Arrays.toString(meetingRooms) +
                ", meetingTime='" + meetingTime + '\'' +
                '}';
    }
}
