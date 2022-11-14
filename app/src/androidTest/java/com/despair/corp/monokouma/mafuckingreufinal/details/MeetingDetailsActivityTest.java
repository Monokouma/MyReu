package com.despair.corp.monokouma.mafuckingreufinal.details;

import androidx.annotation.Nullable;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.ui.details.MeetingDetailsActivity;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.ListMeetingActivity;
import com.despair.corp.monokouma.mafuckingreufinal.utils.ClickChildViewWithId;
import com.despair.corp.monokouma.mafuckingreufinal.utils.CreateMeetingTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.utils.EditTextErrorMatcher;
import com.despair.corp.monokouma.mafuckingreufinal.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingDetailsActivityTest {

    private MeetingDetailsActivity meetingDetailsActivity;
    private ListMeetingActivity listMeetingActivity;

    @Before
    public void setUp() {
        ActivityScenario<MeetingDetailsActivity> meetingDetailsActivityActivityScenario = ActivityScenario.launch(MeetingDetailsActivity.class);
        meetingDetailsActivityActivityScenario.onActivity(activity -> meetingDetailsActivity = activity);

        ActivityScenario<ListMeetingActivity> listMeetingActivityActivityScenario = ActivityScenario.launch(ListMeetingActivity.class);
        listMeetingActivityActivityScenario.onActivity(activity -> listMeetingActivity = activity);
    }

    @After
    public void teardown() {
        meetingDetailsActivity = null;
    }

    @Test
    public void open_meeting_details() throws InterruptedException {
        LocalTime localTimeFirstMeeting = LocalTime.of(14, 30);
        LocalTime localTimeSecondMeeting = LocalTime.of(16, 30);

        create_meeting("foo", "bar, baz", Room.Pekin, localTimeFirstMeeting);

        create_meeting("aName", "aParticipant, aSecondParticipant, aThirdParticipant", Room.Chicago, localTimeSecondMeeting);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
        );

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        );

        Log.i("Monokouma", LocalTime.now().plusMinutes(30).format(DateTimeFormatter.ofPattern("HH:mm")));


        onView(
                withId(R.id.meeting_details_meeting_title)
        ).check(
                matches(
                        
                        isDisplayed()
                )
        );

    }

    private void create_meeting(
            @Nullable String meetingName,
            @Nullable String meetingParticipants,
            @Nullable Room meetingRoom,
            @Nullable LocalTime meetingTime) {

        onView(
                withId(R.id.activity_list_meeting_add_meeting_fab)
        ).perform(
                click()
        );

        CreateMeetingTestUtils.createMeeting(meetingName, meetingParticipants, meetingRoom, meetingTime);

        onView(
                withId(R.id.create_meeting_create_button)
        ).perform(
                click()
        );

    }

}
