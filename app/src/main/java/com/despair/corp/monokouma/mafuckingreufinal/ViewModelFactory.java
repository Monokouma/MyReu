package com.despair.corp.monokouma.mafuckingreufinal;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.despair.corp.monokouma.mafuckingreufinal.data.repository.MeetingRepository;
import com.despair.corp.monokouma.mafuckingreufinal.ui.create.CreateMeetingViewModel;
import com.despair.corp.monokouma.mafuckingreufinal.ui.details.MeetingDetailsViewModel;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.ListMeetingViewModel;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            new MeetingRepository()
                    );
                }
            }
        }
        return factory;
    }

    @NonNull
    private final MeetingRepository meetingRepository;

    private ViewModelFactory(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;

    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListMeetingViewModel.class)) {
            return (T) new ListMeetingViewModel(
                    meetingRepository,
                    MainApplication.getInstance().getResources()
            );
        } else if (modelClass.isAssignableFrom(CreateMeetingViewModel.class)) {
               return (T) new CreateMeetingViewModel(
                       meetingRepository,
                       MainApplication.getInstance().getResources()
               );
        } else if (modelClass.isAssignableFrom(MeetingDetailsViewModel.class)) {
            return (T) new MeetingDetailsViewModel(
                    meetingRepository,
                    MainApplication.getInstance().getResources()
            );
        }
        //else if (modelClass.isAssignableFrom(MeetingDetailsViewModel.class)) {
        //   return (T) new MeetingDetailsViewModel(
        // );
        // }
        throw new IllegalArgumentException("Unknow ViewModel");
    }

}
