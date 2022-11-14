package com.despair.corp.monokouma.mafuckingreufinal.create;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.ui.create.CreateMeetingActivity;
import com.despair.corp.monokouma.mafuckingreufinal.utils.CreateMeetingTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.utils.EditTextErrorMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateMeetingActivityTest {

    private CreateMeetingActivity activityRef;

    @Before
    public void setUp() {
        ActivityScenario<CreateMeetingActivity> activityScenario = ActivityScenario.launch(CreateMeetingActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);
    }

    @After
    public void teardown() {
        activityRef = null;
    }

    @Test
    public void nominal_case() {
        LocalTime localTime = LocalTime.of(8, 30);

        CreateMeetingTestUtils.createMeeting("foo", "bar, baz", Room.Moscou, localTime);

        //Ask nino : comment trouver le bouton du pop up de l'heure

        onView(
                withId(R.id.create_meeting_create_button)
        ).perform(
                click()
        );

        assertTrue(activityRef.isFinishing());


    }

    @Test
    public void on_empty_user_input() {
        LocalTime localTime = LocalTime.of(8, 30);
        //Full empty
        CreateMeetingTestUtils.createMeeting("", "", null, null);

        onView(withId(R.id.create_meeting_create_button)).perform(click());

        assertFalse(activityRef.isFinishing());

        onView(withId(R.id.create_meeting_name_meeting_tiet)).check(matches(new EditTextErrorMatcher(R.string.error_meeting_name)));
        onView(withId(R.id.create_meeting_participants_name_tiet)).check(matches(new EditTextErrorMatcher(R.string.error_participants)));

      //  Pourquoi ca marche plus :(
      //  onView(withId(R.id.create_meeting_room_actv)).check(matches(new EditTextErrorMatcher(R.string.error_room)));


        //Only meeting name is filled
        CreateMeetingTestUtils.createMeeting("foo", "", null, null);

        onView(withId(R.id.create_meeting_create_button)).perform(click());

        assertFalse(activityRef.isFinishing());
        onView(withId(R.id.create_meeting_name_meeting_tiet)).check(matches(not(new EditTextErrorMatcher(R.string.error_meeting_name))));
        onView(withId(R.id.create_meeting_participants_name_tiet)).check(matches(new EditTextErrorMatcher(R.string.error_participants)));
  //     onView(withId(R.id.create_meeting_room_actv)).check(matches(not(withText(R.string.error_room))));


        //Only meeting name and meeting particpant are filled
        CreateMeetingTestUtils.createMeeting("foo", "bar, baz", null, null);

        onView(withId(R.id.create_meeting_create_button)).perform(click());

        assertFalse(activityRef.isFinishing());
        onView(withId(R.id.create_meeting_name_meeting_tiet)).check(matches(not(new EditTextErrorMatcher(R.string.error_meeting_name))));
        onView(withId(R.id.create_meeting_participants_name_tiet)).check(matches(not(new EditTextErrorMatcher(R.string.error_participants))));
   //     onView(withId(R.id.create_meeting_room_actv)).check(matches(not(withText(R.string.error_room))));

        //Full filled
        CreateMeetingTestUtils.createMeeting("foo", "bar, baz", Room.Pekin, localTime);

        onView(withId(R.id.create_meeting_name_meeting_tiet)).check(matches(not(new EditTextErrorMatcher(R.string.error_meeting_name))));
        onView(withId(R.id.create_meeting_participants_name_tiet)).check(matches(not(new EditTextErrorMatcher(R.string.error_participants))));
        onView(withId(R.id.create_meeting_room_actv)).check(matches(not(new EditTextErrorMatcher(R.string.error_room))));

        onView(withId(R.id.create_meeting_create_button)).perform(click());
        assertTrue(activityRef.isFinishing());


    }

}

