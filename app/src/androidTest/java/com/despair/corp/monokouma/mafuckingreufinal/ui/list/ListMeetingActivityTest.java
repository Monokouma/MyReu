package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.is;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.create.CreateMeetingTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;

import org.hamcrest.Matcher;
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
    public void create_one_meeting_recycler_view_should_have_one_item() {
        create_meeting("foo", "bar, baz", Room.Pekin);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );
    }

    @Test
    public void create_two_meeting_then_delete_one_recycler_view_should_have_one_item() {
        create_meeting("foo", "bar, baz", Room.Pekin);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );

        create_meeting("aName", "aParticipant, aOtherParticipant", Room.Singapour);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
        );

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, click())
        );
    }

    private void create_meeting(
            @Nullable String meetingName,
            @Nullable String meetingParticipants,
            @Nullable Room meetingRoom) {

        onView(
                withId(R.id.activity_list_meeting_add_meeting_fab)
        ).perform(
                click()
        );

        CreateMeetingTestUtils.createMeeting(meetingName, meetingParticipants, meetingRoom);

        onView(
                withId(R.id.create_meeting_create_button)
        ).perform(
                click()
        );

    }

}

class RecyclerViewItemCountAssertion implements ViewAssertion, ViewAction {
    private final int expectedCount;

    public RecyclerViewItemCountAssertion(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), is(expectedCount));

    }

    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(UiController uiController, View view) {

    }
}
