package com.despair.corp.monokouma.mafuckingreufinal.utils;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter.HourFilterAdapter;

import org.hamcrest.Description;

public class HourFilterViewHolderMatcher extends BoundedMatcher<RecyclerView.ViewHolder, HourFilterAdapter.ViewHolder> {

    @NonNull
    private final String hour;

    public HourFilterViewHolderMatcher(@NonNull String hour) {
        super(HourFilterAdapter.ViewHolder.class);

        this.hour = hour;
    }

    @Override
    protected boolean matchesSafely(@NonNull HourFilterAdapter.ViewHolder item) {
        TextView textViewHour = item.itemView.findViewById(R.id.meeting_hour_filter_tv_hour);

        if (textViewHour == null) {
            return false;
        }

        return hour.equals(textViewHour.getText().toString());
    }

    @Override
    public void describeTo(@NonNull Description description) {
        description.appendText("ViewHolder with text = " + hour);
    }
}
