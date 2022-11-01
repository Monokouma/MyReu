package com.despair.corp.monokouma.mafuckingreufinal.ui.details.participant_adapter;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ParticipantViewStateItem {

    @NonNull
    private final String participant;

    public ParticipantViewStateItem(@NonNull String participant) {
        this.participant = participant;
    }

    @NonNull
    public String getParticipant() {
        return participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantViewStateItem that = (ParticipantViewStateItem) o;
        return participant.equals(that.participant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participant);
    }

    @NonNull
    @Override
    public String toString() {
        return "ParticipantViewStateItem{" +
                "participant='" + participant + '\'' +
                '}';
    }
}
