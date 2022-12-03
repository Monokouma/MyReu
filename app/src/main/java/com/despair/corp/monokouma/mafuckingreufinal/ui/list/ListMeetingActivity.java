package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.ViewModelFactory;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.ui.create.CreateMeetingActivity;
import com.despair.corp.monokouma.mafuckingreufinal.ui.details.MeetingDetailsActivity;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter.HourFilterAdapter;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter.OnHourSelectedListener;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.meeting_items.OnMeetingItemsClick;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.room_filter.OnRoomSelectedListenner;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.room_filter.RoomFilterAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;

public class ListMeetingActivity extends AppCompatActivity implements OnMeetingItemsClick, OnHourSelectedListener, OnRoomSelectedListenner {

    private ConstraintLayout constraintLayout;
    private RecyclerView meetingRecyclerView;
    private RecyclerView roomRecyclerView;
    private RecyclerView hourRecyclerView;
    private ListMeetingViewModel listMeetingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        listMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ListMeetingViewModel.class);
        Toolbar toolbar = findViewById(R.id.activity_list_meeting_app_bar_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addMeetingFab = findViewById(R.id.activity_list_meeting_add_meeting_fab);
        addMeetingFab.setOnClickListener(v -> startActivity(CreateMeetingActivity.navigate(this)));

        meetingRecyclerView = findViewById(R.id.activity_list_meeting_recycler_view);
        roomRecyclerView = findViewById(R.id.activity_list_meeting_rooms_recycler_view);
        hourRecyclerView = findViewById(R.id.activity_list_meeting_hours_recycler_view);
        constraintLayout = findViewById(R.id.activity_list_meeting_root);

        initRecyclerViews(listMeetingViewModel, meetingRecyclerView, roomRecyclerView, hourRecyclerView);
    }

    private void initRecyclerViews(ListMeetingViewModel listMeetingViewModel, RecyclerView meetingRecyclerView, RecyclerView roomRecyclerView, RecyclerView hourRecyclerView) {
        final MeetingsListAdapter meetingsListAdapter = new MeetingsListAdapter(this);
        meetingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        meetingRecyclerView.setAdapter(meetingsListAdapter);

        final RoomFilterAdapter roomFilterAdapter = new RoomFilterAdapter(this);
        roomRecyclerView.setAdapter(roomFilterAdapter);

        final HourFilterAdapter hourFilterAdapter = new HourFilterAdapter(this);
        hourRecyclerView.setAdapter(hourFilterAdapter);

        listMeetingViewModel.getListMeetingViewStateMediatorLiveData().observe(this, listMeetingViewState -> {
            meetingsListAdapter.submitList(listMeetingViewState.getMeetingsViewStateItem());
            roomFilterAdapter.submitList(listMeetingViewState.getMeetingViewStateRoomFilterItems());
            hourFilterAdapter.submitList(listMeetingViewState.getMeetingViewStateHourFilterItems());
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.meeting_menu_room) {
            changeVisibilityWithAnimation(roomRecyclerView);
            return true;
        } else if (itemId == R.id.meeting_hour_filter) {
            changeVisibilityWithAnimation(hourRecyclerView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    private void changeVisibilityWithAnimation(@NonNull View view) {
        boolean isViewActuallyVisible = view.getVisibility() == View.VISIBLE;

        TransitionManager.endTransitions(constraintLayout);

        TransitionManager.beginDelayedTransition(constraintLayout);

        if (isViewActuallyVisible) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDeleteMeeting(int meetingId) {
        listMeetingViewModel.onDeleteButtonClick(meetingId);
    }

    @Override
    public void onMeetingClicked(int meetingId) {
        startActivity(MeetingDetailsActivity.navigate(this, meetingId));
    }

    @Override
    public void onHourSelected(@NonNull LocalTime hour) {
        listMeetingViewModel.onHourSelected(hour);
    }

    @Override
    public void onRoomSelected(@NonNull Room room) {
        listMeetingViewModel.onRoomSelected(room);
    }
}