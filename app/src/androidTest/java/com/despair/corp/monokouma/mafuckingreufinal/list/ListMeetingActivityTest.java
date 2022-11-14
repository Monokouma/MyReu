package com.despair.corp.monokouma.mafuckingreufinal.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.is;

import androidx.annotation.Nullable;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.ListMeetingActivity;
import com.despair.corp.monokouma.mafuckingreufinal.utils.CreateMeetingTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.utils.ClickChildViewWithId;
import com.despair.corp.monokouma.mafuckingreufinal.utils.HourFilterViewHolderMatcher;
import com.despair.corp.monokouma.mafuckingreufinal.utils.RecyclerViewItemCountAssertion;
import com.despair.corp.monokouma.mafuckingreufinal.utils.RoomFilterViewHolderMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;

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
        LocalTime localTime = LocalTime.of(8, 30);

        create_meeting("foo", "bar, baz", Room.Pekin, localTime);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );
    }

    @Test
    public void create_two_meeting_then_delete_one_recycler_view_should_have_one_item() {
        LocalTime localTime = LocalTime.of(8, 30);

        //Create first meeting
        create_meeting("foo", "bar, baz", Room.Pekin, localTime);

        //Check recycler view has 1 child
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );

        //Create second meeting
        create_meeting("aName", "aParticipant, aOtherParticipant", Room.Singapour, localTime);

        //Check recycler view has 2 child
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
        );

        //Click on first element then delete it
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, new ClickChildViewWithId(R.id.meetings_item_delete_button))
        );

        //Check recycler view has one child
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );
    }

    @Test
    public void create_two_meeting_then_filter_by_room_name() {
        LocalTime localTime = LocalTime.of(8, 30);

        create_meeting("foo", "bar, baz", Room.Pekin, localTime);
        create_meeting("aName", "aParticipant, aOtherParticipant", Room.Singapour, localTime);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
        );

        onView(
                withId(R.id.meeting_menu_room)
        ).perform(
                click()
        );

        //Apply filter
        onView(
                withId(R.id.activity_list_meeting_rooms_recycler_view)
        ).perform(
                scrollToHolder(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName())),
                RecyclerViewActions.actionOnHolderItem(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName()), click())
        );

        //Check if the recycler viex has 1 child
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );

        //Back to normal state
        onView(
                withId(R.id.activity_list_meeting_rooms_recycler_view)
        ).perform(
                scrollToHolder(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName())),
                RecyclerViewActions.actionOnHolderItem(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName()), click())
        );

        //Check RV has 2 child again
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
        );
    }

    @Test
    public void create_two_meetings_then_filter_by_meeting_schedule() {
        LocalTime localTimeFirstMeeting = LocalTime.of(14, 30);
        LocalTime localTimeSecondMeeting = LocalTime.of(16, 30);

        create_meeting("foo", "bar, baz", Room.Pekin, localTimeFirstMeeting);
        create_meeting("aName", "aParticipant, aOtherParticipant", Room.Singapour, localTimeSecondMeeting);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
        );

        onView(
                withId(R.id.meeting_hour_filter)
        ).perform(
                click()
        );

        //Apply filter
        onView(
                withId(R.id.activity_list_meeting_hours_recycler_view)
        ).perform(
                scrollToHolder(new HourFilterViewHolderMatcher("10:00")),
                RecyclerViewActions.actionOnHolderItem(new HourFilterViewHolderMatcher("10:00"), click())
        );

        //Check RV has no child
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(0)
        );

        //Back to normal state
        onView(
                withId(R.id.activity_list_meeting_hours_recycler_view)
        ).perform(
                scrollToHolder(new HourFilterViewHolderMatcher("10:00")),
                RecyclerViewActions.actionOnHolderItem(new HourFilterViewHolderMatcher("10:00"), click())
        );

        //Check RV has 2 child again
        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
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

