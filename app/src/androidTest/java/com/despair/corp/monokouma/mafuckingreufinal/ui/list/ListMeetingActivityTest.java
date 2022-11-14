package com.despair.corp.monokouma.mafuckingreufinal.ui.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.create.CreateMeetingTestUtils;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.hour_filter.HourFilterAdapter;
import com.despair.corp.monokouma.mafuckingreufinal.ui.list.room_filter.RoomFilterAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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

        create_meeting("foo", "bar, baz", Room.Pekin, localTime);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );

        create_meeting("aName", "aParticipant, aOtherParticipant", Room.Singapour, localTime);

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(2)
        );

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, new ClickChildViewWithId(R.id.meetings_item_delete_button))
        );

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

        onView(
                withId(R.id.activity_list_meeting_rooms_recycler_view)
        ).perform(
                scrollToHolder(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName())),
                RecyclerViewActions.actionOnHolderItem(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName()), click())
        );

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(1)
        );

        onView(
                withId(R.id.activity_list_meeting_rooms_recycler_view)
        ).perform(
                scrollToHolder(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName())),
                RecyclerViewActions.actionOnHolderItem(new RoomFilterViewHolderMatcher(Room.Pekin.getStringResName()), click())
        );

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

        onView(
                withId(R.id.activity_list_meeting_hours_recycler_view)
        ).perform(
                scrollToHolder(new HourFilterViewHolderMatcher("10:00")),
                RecyclerViewActions.actionOnHolderItem(new HourFilterViewHolderMatcher("10:00"), click())
        );

        onView(
                withId(R.id.activity_list_meeting_recycler_view)
        ).check(
                new RecyclerViewItemCountAssertion(0)
        );

        onView(
                withId(R.id.activity_list_meeting_hours_recycler_view)
        ).perform(
                scrollToHolder(new HourFilterViewHolderMatcher("10:00")),
                RecyclerViewActions.actionOnHolderItem(new HourFilterViewHolderMatcher("10:00"), click())
        );

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

class ClickChildViewWithId implements ViewAction {

    @IdRes
    private final int viewId;

    public ClickChildViewWithId(@IdRes int viewId) {
        this.viewId = viewId;
    }

    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on a child view with specified id : " + viewId;
    }

    @Override
    public void perform(UiController uiController, View view) {
        View v = view.findViewById(viewId);

        v.performClick();
    }
}

class RoomFilterViewHolderMatcher extends BoundedMatcher<RecyclerView.ViewHolder, RoomFilterAdapter.ViewHolder> {

    @StringRes
    private final int titleStringRes;

    private String resolvedTitleString;

    public RoomFilterViewHolderMatcher(@StringRes int titleStringRes) {
        super(RoomFilterAdapter.ViewHolder.class);

        this.titleStringRes = titleStringRes;
    }

    @Override
    protected boolean matchesSafely(@NonNull RoomFilterAdapter.ViewHolder item) {
        Context context = item.itemView.getContext();

        try {
            resolvedTitleString = context.getString(titleStringRes);
        } catch (Resources.NotFoundException exception) {
            exception.printStackTrace();

            return false;
        }

        return resolvedTitleString.equals(item.chip.getText().toString());
    }

    @Override
    public void describeTo(@NonNull Description description) {
        description.appendText("ViewHolder with text = " + resolvedTitleString);
    }
}

class HourFilterViewHolderMatcher extends BoundedMatcher<RecyclerView.ViewHolder, HourFilterAdapter.ViewHolder> {

    @NonNull
    private final String hour;

    public HourFilterViewHolderMatcher(@NonNull String hour) {
        super(HourFilterAdapter.ViewHolder.class);

        this.hour = hour;
    }

    @Override
    protected boolean matchesSafely(@NonNull HourFilterAdapter.ViewHolder item) {
        TextView textViewHour = item.itemView.findViewById(R.id.meeting_hour_filter_tv_hour);

        if (textViewHour == null) {
            return false;
        }

        return hour.equals(textViewHour.getText().toString());
    }

    @Override
    public void describeTo(@NonNull Description description) {
        description.appendText("ViewHolder with text = " + hour);
    }
}
