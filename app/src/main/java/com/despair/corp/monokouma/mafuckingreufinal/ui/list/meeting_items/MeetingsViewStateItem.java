package com.despair.corp.monokouma.mafuckingreufinal.ui.list.meeting_items;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.Objects;

public class MeetingsViewStateItem {

    private final int meetingId;

    @DrawableRes
    private final int meetingBackground;

    @DrawableRes
    private final int meetingIcon;

    @NonNull
    private final String name;

    @NonNull
    private final String participants;

    public MeetingsViewStateItem(
            int meetingId,
            @DrawableRes int meetingIcon,
            @NonNull String name,
            @NonNull String participants,
            @DrawableRes int meetingBackground
    ) {
        this.meetingId = meetingId;
        this.meetingIcon = meetingIcon;
        this.name = name;
        this.participants = participants;
        this.meetingBackground = meetingBackground;
    }

    public int getMeetingId() {
        return meetingId;
    }

    @DrawableRes
    public int getMeetingIcon() {
        return meetingIcon;
    }

    @NonNull
    public String getMeetingName() {
        return name;
    }

    @NonNull
    public String getParticipants() {
        return participants;
    }

    public int getMeetingBackground() {
        return meetingBackground;
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingsViewStateItem{" +
                "meetingId=" + meetingId +
                ", meetingBackground=" + meetingBackground +
                ", meetingIcon=" + meetingIcon +
                ", name='" + name + '\'' +
                ", participants='" + participants + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingsViewStateItem that = (MeetingsViewStateItem) o;
        return meetingId == that.meetingId
            && meetingBackground == that.meetingBackground
            && meetingIcon == that.meetingIcon
            && name.equals(that.name)
            && participants.equals(that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, meetingBackground, meetingIcon, name, participants);
    }

}
