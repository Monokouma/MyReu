package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.meeting_items.MeetingsViewStateItem;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.meeting_items.OnMeetingItemsClick;
import com.google.android.material.imageview.ShapeableImageView;

public class MeetingsListAdapter extends ListAdapter<MeetingsViewStateItem, MeetingsListAdapter.ViewHolder> {

    OnMeetingItemsClick listenner;

    public MeetingsListAdapter(OnMeetingItemsClick onItemClicked) {
        super(new MeetingsListAdapterDiffCallback());
        this.listenner = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.meeting_items_view, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listenner);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView roomImage;
        private final TextView meetingNameTv;
        private final TextView participantsTv;
        private final ImageView deleteImage;
        private final ConstraintLayout meetingLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.meeting_avatar);
            meetingNameTv = itemView.findViewById(R.id.meeting_title);
            participantsTv = itemView.findViewById(R.id.meeting_email);
            deleteImage = itemView.findViewById(R.id.meetings_item_delete_button);
            meetingLayout = itemView.findViewById(R.id.meetin_layout);

        }

        public void bind(@NonNull final MeetingsViewStateItem meetingViewStateItem, @NonNull OnMeetingItemsClick onItemClicked) {
            roomImage.setImageResource(meetingViewStateItem.getMeetingIcon());
            roomImage.setBackgroundResource(meetingViewStateItem.getMeetingBackground());
            meetingNameTv.setText(meetingViewStateItem.getMeetingName());
            participantsTv.setText(meetingViewStateItem.getParticipants());

            deleteImage.setOnClickListener(view -> onItemClicked.onDeleteMeeting(meetingViewStateItem.getMeetingId()));
            meetingLayout.setOnClickListener(view -> onItemClicked.onMeetingClicked(meetingViewStateItem.getMeetingId()));
        }
    }

    private static class MeetingsListAdapterDiffCallback extends DiffUtil.ItemCallback<MeetingsViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull MeetingsViewStateItem oldItem, @NonNull MeetingsViewStateItem newItem) {
            return oldItem.getMeetingId() == newItem.getMeetingId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingsViewStateItem oldItem, @NonNull MeetingsViewStateItem newItem) {
            return oldItem.getMeetingName().equals(newItem.getMeetingName())
                    && oldItem.getParticipants().equals(newItem.getParticipants())
                    && oldItem.getMeetingIcon() == newItem.getMeetingIcon();
        }
    }


}