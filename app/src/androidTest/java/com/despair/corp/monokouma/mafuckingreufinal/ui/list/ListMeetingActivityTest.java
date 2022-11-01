package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.despair.corp.monokouma.mafuckingreufinal.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListMeetingActivityTest {

    private ListMeetingActivity listMeetingActivity;

    @Before
    public void setUp() {
        ActivityScenario<ListMeetingActivity> listMeetingActivityActivityScenario = ActivityScenario.launch(ListMeetingActivity.class);
        listMeetingActivityActivityScenario.onActivity(activity -> listMeetingActivity = activity);
    }

    @After
    public void teardown() {
        listMeetingActivity = null;
    }

    @Test
    public void test() {
        onView(withId(R.id.activity_list_meeting_add_meeting_fab)).perform(click());
    }
}
