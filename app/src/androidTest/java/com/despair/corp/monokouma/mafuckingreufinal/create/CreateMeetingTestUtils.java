package com.despair.corp.monokouma.mafuckingreufinal.create;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.util.Log;

import androidx.annotation.Nullable;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;

import java.time.LocalTime;

public class CreateMeetingTestUtils {

    public static void createMeeting(
            @Nullable String meetingName,
            @Nullable String meetingParticipants,
            @Nullable Room meetingRoom,
            @Nullable LocalTime meetingSchedule) {

        if (meetingName != null) {
            onView(
                    withId(R.id.create_meeting_name_meeting_tiet)
            ).perform(
                    replaceText(meetingName),
                    closeSoftKeyboard()
            );
        }

        if (meetingParticipants != null) {
            onView(
                    withId(R.id.create_meeting_participants_name_tiet)
            ).perform(
                    replaceText(meetingParticipants),
                    closeSoftKeyboard()
            );
        }

        if (meetingRoom != null) {
            onView(
                    withId(R.id.create_meeting_room_til)
            ).perform(
                    click()
            );

            onData(
                    is(meetingRoom)
            ).inRoot(
                    isPlatformPopup()
            ).perform(
                    click()
            );
        }

        if (meetingSchedule != null) {



        }
    }
}
