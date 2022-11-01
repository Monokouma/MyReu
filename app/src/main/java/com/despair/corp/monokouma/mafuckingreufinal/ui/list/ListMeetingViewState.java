package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import androidx.annotation.NonNull;

import com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter.MeetingViewStateHourFilterItem;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.meeting_items.MeetingsViewStateItem;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.room_filter.MeetingViewStateRoomFilterItem;

import java.util.List;
import java.util.Objects;

public class ListMeetingViewState {

    @NonNull
    private final List<MeetingsViewStateItem> meetingsViewStateItem;

    @NonNull
    private final List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems;

    @NonNull
    private final List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems;

    public ListMeetingViewState(@NonNull List<MeetingsViewStateItem> meetingsViewStateItem, @NonNull List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems, @NonNull List<MeetingViewStateHourFilterItem> meetingViewStateHourFilterItems) {
        this.meetingsViewStateItem = meetingsViewStateItem;
        this.meetingViewStateRoomFilterItems = meetingViewStateRoomFilterItems;
        this.meetingViewStateHourFilterItems = meetingViewStateHourFilterItems;
    }

    @NonNull
    public List<MeetingsViewStateItem> getMeetingsViewStateItem() {
        return meetingsViewStateItem;
    }

    @NonNull
    public List<MeetingViewStateRoomFilterItem> getMeetingViewStateRoomFilterItems() {
        return meetingViewStateRoomFilterItems;
    }

    @NonNull
    public List<MeetingViewStateHourFilterItem> getMeetingViewStateHourFilterItems() {
        return meetingViewStateHourFilterItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListMeetingViewState that = (ListMeetingViewState) o;
        return meetingsViewStateItem.equals(that.meetingsViewStateItem) && meetingViewStateRoomFilterItems.equals(that.meetingViewStateRoomFilterItems) && meetingViewStateHourFilterItems.equals(that.meetingViewStateHourFilterItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingsViewStateItem, meetingViewStateRoomFilterItems, meetingViewStateHourFilterItems);
    }

    @NonNull
    @Override
    public String toString() {
        return "ListMeetingViewState{" +
                "meetingsViewStateItem=" + meetingsViewStateItem +
                ", meetingViewStateRoomFilterItems=" + meetingViewStateRoomFilterItems +
                ", meetingViewStateHourFilterItems=" + meetingViewStateHourFilterItems +
                '}';
    }
}
