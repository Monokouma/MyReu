package com.despair.corp.monokouma.mafuckingreufinal.list;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.despair.corp.monokouma.mafuckingreufinal.ui.list.ListMeetingActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)

public class ListMeetingActivityTest {

    private ListMeetingActivity listMeetingActivity;

    @Before
    public void set_up() {
        ActivityScenario<ListMeetingActivity> listMeetingActivityActivityScenario = ActivityScenario.launch(ListMeetingActivity.class);
        listMeetingActivityActivityScenario.onActivity(new ActivityScenario.ActivityAction<ListMeetingActivity>() {
            @Override
            public void perform(ListMeetingActivity activity) {
                listMeetingActivity = activity;
            }
        });
    }

    @Test
    public void test() {
        System.out.println(listMeetingActivity);
    }
}
