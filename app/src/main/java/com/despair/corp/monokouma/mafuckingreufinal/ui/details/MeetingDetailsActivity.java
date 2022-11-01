package com.despair.corp.monokouma.mafuckingreufinal.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.ViewModelFactory;
import com.despair.corp.monokouma.mafuckingreufinal.ui.details.participant_adapter.ParticipantAdapter;
import com.google.android.material.imageview.ShapeableImageView;

public class MeetingDetailsActivity extends AppCompatActivity {

    private final static String ARGS_MEETINGID = "ARGS_MEETINGID";
    RecyclerView participantsRecyclerView;
    //
    public static Intent navigate(Context c, int meetingId) {
        Intent intent = new Intent(c, MeetingDetailsActivity.class);
        intent.putExtra(ARGS_MEETINGID, meetingId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        final MeetingDetailsViewModel meetingDetailsViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingDetailsViewModel.class);

        ShapeableImageView meetingIcon = findViewById(R.id.meeting_details_meeting_image);
        TextView meetingName = findViewById(R.id.meeting_details_meeting_title);
        participantsRecyclerView = findViewById(R.id.meeting_detail_participant_list);
        final ParticipantAdapter participantAdapter = new ParticipantAdapter();
        participantsRecyclerView.setAdapter(participantAdapter);
        TextView scheduleMessage = findViewById(R.id.meeting_detail_meeting_schedule);
        Toolbar toolbar = findViewById(R.id.meeting_detail_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());


        int meetingId = getIntent().getIntExtra(ARGS_MEETINGID, -1);

        meetingDetailsViewModel.onDetailsLoad(meetingId);

        meetingDetailsViewModel.getMeetingDetailsViewStateMutableLiveData().observe(this, new Observer<MeetingDetailsViewState>() {
            @Override
            public void onChanged(MeetingDetailsViewState meetingDetailsViewState) {
                meetingIcon.setImageResource(meetingDetailsViewState.getMeetingIcon());
                meetingIcon.setBackgroundResource(meetingDetailsViewState.getMeetingBackground());
                meetingName.setText(meetingDetailsViewState.getName());
                scheduleMessage.setText(meetingDetailsViewState.getMeetingSchedule());
                participantAdapter.submitList(meetingDetailsViewState.getParticipants());
            }
        });
    }
}