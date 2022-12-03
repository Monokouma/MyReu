package com.despair.corp.monokouma.mafuckingreufinal.ui.list.room_filter;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;

import java.util.Objects;

public class MeetingViewStateRoomFilterItem {

    @NonNull
    private final Room room;

    @ColorInt
    private final int textColorInt;

    private final boolean isSelected;

    public MeetingViewStateRoomFilterItem(@NonNull Room room,
                                          @ColorInt int textColorInt,
                                          boolean isSelected) {
        this.room = room;
        this.textColorInt = textColorInt;
        this.isSelected = isSelected;
    }

    @NonNull
    public Room getRoom() {
        return room;
    }

    @ColorInt
    public int getTextColorInt() {
        return textColorInt;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewStateRoomFilterItem that = (MeetingViewStateRoomFilterItem) o;
        return textColorInt == that.textColorInt
            && isSelected == that.isSelected
            && room == that.room;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room,
            textColorInt,
            isSelected);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingViewStateRoomFilterItem{" +
                "room=" + room +
                ", textColorInt=" + textColorInt +
                ", isSelected=" + isSelected +
                '}';
    }
}
