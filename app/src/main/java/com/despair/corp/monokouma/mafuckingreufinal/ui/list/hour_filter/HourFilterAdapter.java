package com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.google.android.material.button.MaterialButton;

public class HourFilterAdapter extends ListAdapter<MeetingViewStateHourFilterItem, HourFilterAdapter.ViewHolder> {

    @NonNull
    private final OnHourSelectedListener listener;

    public HourFilterAdapter(@NonNull OnHourSelectedListener listener) {
        super(new HourFilterAdapterDiffCallback());

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_hour_filter_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialButton textViewHour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHour = itemView.findViewById(R.id.meeting_hour_filter_tv_hour);
        }

        @SuppressLint("ResourceType")
        public void bind(@NonNull final MeetingViewStateHourFilterItem item, @NonNull final OnHourSelectedListener listener) {
            textViewHour.setText(item.getHour());
            textViewHour.setBackgroundColor(ContextCompat.getColor(textViewHour.getContext(), item.getDrawableResBackground()));
            textViewHour.setTextColor(ContextCompat.getColor(textViewHour.getContext(), item.getTextColorRes()));
            textViewHour.setOnClickListener(v -> listener.onHourSelected(item.getHourLocalTime()));
        }
    }

    private static class HourFilterAdapterDiffCallback extends DiffUtil.ItemCallback<MeetingViewStateHourFilterItem> {
        @Override
        public boolean areItemsTheSame(@NonNull MeetingViewStateHourFilterItem oldItem, @NonNull MeetingViewStateHourFilterItem newItem) {
            return oldItem.getHour().equals(newItem.getHour());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingViewStateHourFilterItem oldItem, @NonNull MeetingViewStateHourFilterItem newItem) {
            return oldItem.getDrawableResBackground() == newItem.getDrawableResBackground()
                    && oldItem.getTextColorRes() == newItem.getTextColorRes();
        }
    }
}
