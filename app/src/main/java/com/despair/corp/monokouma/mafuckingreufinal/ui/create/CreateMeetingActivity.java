package com.despair.corp.monokouma.mafuckingreufinal.ui.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.ViewModelFactory;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.ui.create.room.RoomSpinnerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;

public class CreateMeetingActivity extends AppCompatActivity {

    public static Intent navigate(Context c) {
        Intent intent = new Intent(c, CreateMeetingActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        final CreateMeetingViewModel createMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);
        Toolbar toolbar = findViewById(R.id.create_meeting_toolbar);
        TextInputEditText meetingNameInput = findViewById(R.id.create_meeting_name_meeting_tiet);
        TextInputEditText participantsNameInput = findViewById(R.id.create_meeting_participants_name_tiet);
        AutoCompleteTextView roomInput = findViewById(R.id.create_meeting_room_actv);
        TextView meetingHourTv = findViewById(R.id.create_meeting_hour_tv);
        ImageButton setMeetingHour = findViewById(R.id.create_meeting_set_hour);
        MaterialButton createMeetingButton = findViewById(R.id.create_meeting_create_button);

        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.chose_meeting_hour))
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .setPositiveButtonText(getString(R.string.chose))
                .setNegativeButtonText(getString(R.string.cancel))
                .build();

        toolbar.setNavigationOnClickListener(v -> finish());

        setMeetingHour.setOnClickListener(view -> {
            picker.show(getSupportFragmentManager(), "TimePicker");

            picker.addOnPositiveButtonClickListener(view2 -> {

                LocalTime meetingSchedule = LocalTime.of(picker.getHour(), picker.getMinute());


                createMeetingViewModel.onMeetingScheduleChanged(meetingSchedule);
                hideKeyboard(this, view);
            });
        });

        initMeetingNameEditText(createMeetingViewModel, meetingNameInput);
        createMeeting(createMeetingViewModel, createMeetingButton);
        initParticipantsEditText(createMeetingViewModel, participantsNameInput);


        createMeetingViewModel.getCreateMeetingViewStateMutableLiveData().observe(this, createMeetingViewState -> {
            initRoomAutocompleteView(createMeetingViewModel, roomInput, createMeetingViewState.getMeetingRooms());
            meetingHourTv.setText(createMeetingViewState.getMeetingTime());
            meetingNameInput.setError(createMeetingViewState.getMeetingNameError());
            participantsNameInput.setError(createMeetingViewState.getMeetingParticipantsError());
            roomInput.setError(createMeetingViewState.getMeetingNameError());
        });

        createMeetingViewModel.getOnActivityFinishEvent().observe(this, aVoid -> {
            finish();
        });
    }

    private void initParticipantsEditText(@NonNull CreateMeetingViewModel createMeetingViewModel, @NonNull EditText participantsEditText) {
        participantsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                createMeetingViewModel.onMeetingParticipantTextChanged(s.toString());
            }
        });
    }

    private void initRoomAutocompleteView(
            @NonNull CreateMeetingViewModel createMeetingViewModel,
            @NonNull AutoCompleteTextView autoCompleteTextView,
            @NonNull Room[] rooms
    ) {
        final RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(this, rooms);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                createMeetingViewModel.onMeetingRoomItemClicked(adapter.getItem(position));
        });
    }

    private void initMeetingNameEditText(@NonNull CreateMeetingViewModel createMeetingViewModel, @NonNull EditText topicEditText) {
        topicEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                createMeetingViewModel.onMeetingNameTextChanged(s.toString());
            }
        });
    }

    private void createMeeting(@NonNull CreateMeetingViewModel createMeetingViewModel, @NonNull MaterialButton button) {
        button.setOnClickListener(view -> {
            createMeetingViewModel.createMeetingButtonClicked();
        });
    }

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}