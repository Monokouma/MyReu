package com.despair.corp.monokouma.mafuckingreufinal.ui.details;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;
import com.despair.corp.monokouma.mafuckingreufinal.ui.details.participant_adapter.ParticipantViewStateItem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MeetingDetailsViewModel extends ViewModel {

    private final Resources resources;

    private final LiveData<MeetingDetailsViewState> meetingDetailsViewStateLiveData;

    private final MutableLiveData<Integer> meetingIdMutableLiveData = new MutableLiveData<>();

    public MeetingDetailsViewModel(MeetingRepository meetingRepository, Resources resources) {
        this.resources = resources;

        meetingDetailsViewStateLiveData = Transformations.map(
                Transformations.switchMap(
                        meetingIdMutableLiveData,
                        meetingId -> meetingRepository.getMeetingsByIdLiveData(meetingId)
                ),
                meeting -> {
                    final String scheduleMessage;

                    LocalTime now = LocalTime.now();

                    //Meeting en cours
                    if (now.isAfter(meeting.getMeetingTime()) && now.isBefore(meeting.getMeetingTime().plusMinutes(30))) {
                        scheduleMessage = resources.getString(R.string.meeting_running);
                    } else if (meeting.getMeetingTime().plusMinutes(30).isBefore(now)) {
                        //Meeting fini
                        scheduleMessage = resources.getString(R.string.meeting_finished);
                    } else if (now.isBefore(meeting.getMeetingTime())) {
                        //Meeting commence dans ...
                        String date = meeting.getMeetingTime().format(DateTimeFormatter.ofPattern("HH:mm", Locale.FRANCE));

                        scheduleMessage = resources.getString(R.string.meeting_start_at, date);
                    } else {
                        scheduleMessage = "";
                    }

                    String meetingTitle = meeting.getMeetingName() + " - " + meeting.getMeetingTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + resources.getString(meeting.getMeetingRoom().getStringResName());

                    List<ParticipantViewStateItem> participantViewStateItem = new ArrayList<>();

                    for (String participant : meeting.getMeetingParticipantsList()) {
                        String participantBuild = new StringBuilder(participant).append("@lamzone.fr").toString();
                        participantViewStateItem.add(new ParticipantViewStateItem(participantBuild));
                    }

                    return new MeetingDetailsViewState(
                            meeting.getMeetingRoom().getColorRes(),
                            meeting.getMeetingRoom().getDrawableResIcon(),
                            meetingTitle,
                            participantViewStateItem,
                            scheduleMessage
                    );
                }
        );

    }

    public void onDetailsLoad(int meetingId) {
        meetingIdMutableLiveData.setValue(meetingId);
    }

    public LiveData<MeetingDetailsViewState> getMeetingDetailsViewStateMutableLiveData() {
        return meetingDetailsViewStateLiveData;
    }

}
