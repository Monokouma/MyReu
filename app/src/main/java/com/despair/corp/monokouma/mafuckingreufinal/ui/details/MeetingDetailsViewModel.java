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

                    String meetingTitle = meeting.getMeetingName()
                        + " - "
                        + meeting.getMeetingTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                        + " - "
                        + resources.getString(meeting.getMeetingRoom().getStringResName());

                    List<ParticipantViewStateItem> participantViewStateItem = new ArrayList<>();

                    for (String participant : meeting.getMeetingParticipantsList()) {

                        String participantBuild = new StringBuilder(participant)
                            .append(resources.getString(R.string.lamzon_mail))
                            .toString();

                        participantViewStateItem.add(new ParticipantViewStateItem(participantBuild));
                    }

                    return new MeetingDetailsViewState(
                            meeting.getMeetingRoom().getColorRes(),
                            meeting.getMeetingRoom().getDrawableResIcon(),
                            meetingTitle,
                            participantViewStateItem
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
