package com.despair.corp.monokouma.mafuckingreufinal.ui.details.participant_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.despair.corp.monokouma.mafuckingreufinal.R;

public class ParticipantAdapter extends ListAdapter<ParticipantViewStateItem, ParticipantAdapter.ViewHolder> {

    public ParticipantAdapter() {
        super(new ParticipantsListAdapterDiffCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.participants_items_view, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView participantName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            participantName = itemView.findViewById(R.id.participant_item_mail);
        }

        public void bind(@NonNull final ParticipantViewStateItem participantViewStateItem) {
            participantName.setText(participantViewStateItem.getParticipant());
        }
    }

    private static class ParticipantsListAdapterDiffCallback extends DiffUtil.ItemCallback<ParticipantViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull ParticipantViewStateItem oldItem, @NonNull ParticipantViewStateItem newItem) {
            return oldItem.getParticipant().equals(newItem.getParticipant());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ParticipantViewStateItem oldItem, @NonNull ParticipantViewStateItem newItem) {
            return oldItem.getParticipant().equals(newItem.getParticipant());

        }
    }
}
