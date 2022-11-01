package com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.Objects;

public class MeetingViewStateHourFilterItem {

    @NonNull
    private final LocalTime hourLocalTime;

    @NonNull
    private final String hour;

    @DrawableRes
    private final int drawableResBackground;

    @ColorRes
    private final int textColorRes;

    public MeetingViewStateHourFilterItem(
        @NonNull LocalTime hourLocalTime,
        @NonNull String hour,
        @DrawableRes int drawableResBackground,
        @ColorRes int textColorRes
    ) {
        this.hourLocalTime = hourLocalTime;
        this.hour = hour;
        this.drawableResBackground = drawableResBackground;
        this.textColorRes = textColorRes;
    }

    @NonNull
    public LocalTime getHourLocalTime() {
        return hourLocalTime;
    }

    @NonNull
    public String getHour() {
        return hour;
    }

    @DrawableRes
    public int getDrawableResBackground() {
        return drawableResBackground;
    }

    @ColorRes
    public int getTextColorRes() {
        return textColorRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewStateHourFilterItem that = (MeetingViewStateHourFilterItem) o;
        return drawableResBackground == that.drawableResBackground
            && textColorRes == that.textColorRes
            && hourLocalTime.equals(that.hourLocalTime)
            && hour.equals(that.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hourLocalTime, hour, drawableResBackground, textColorRes);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingViewStateHourFilterItem{" +
            "hourLocalTime=" + hourLocalTime +
            ", hour='" + hour + '\'' +
            ", drawableResBackground=" + drawableResBackground +
            ", textColorRes=" + textColorRes +
            '}';
    }
}
