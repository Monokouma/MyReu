package com.despair.corp.monokouma.mafuckingreufinal.ui.details;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.despair.corp.monokouma.mafuckingreufinal.ui.details.participant_adapter.ParticipantViewStateItem;

import java.util.List;
import java.util.Objects;

public class MeetingDetailsViewState {
    @DrawableRes
    private final int meetingBackground;

    @DrawableRes
    private final int meetingIcon;

    @NonNull
    private final String name;

    @NonNull
    private final List<ParticipantViewStateItem> participants;

    @NonNull
    private final String meetingSchedule;

    public MeetingDetailsViewState(int meetingBackground, int meetingIcon, @NonNull String name, @NonNull List<ParticipantViewStateItem> participants, @NonNull String meetingSchedule) {
        this.meetingBackground = meetingBackground;
        this.meetingIcon = meetingIcon;
        this.name = name;
        this.participants = participants;
        this.meetingSchedule = meetingSchedule;
    }

    public int getMeetingBackground() {
        return meetingBackground;
    }

    public int getMeetingIcon() {
        return meetingIcon;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public List<ParticipantViewStateItem> getParticipants() {
        return participants;
    }

    @NonNull
    public String getMeetingSchedule() {
        return meetingSchedule;
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingDetailsViewState{" +
                "meetingBackground=" + meetingBackground +
                ", meetingIcon=" + meetingIcon +
                ", name='" + name + '\'' +
                ", participants='" + participants + '\'' +
                ", meetingSchedule='" + meetingSchedule + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingDetailsViewState that = (MeetingDetailsViewState) o;
        return meetingBackground == that.meetingBackground && meetingIcon == that.meetingIcon && name.equals(that.name) && participants.equals(that.participants) && meetingSchedule.equals(that.meetingSchedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingBackground, meetingIcon, name, participants, meetingSchedule);
    }
}
