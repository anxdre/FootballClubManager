package com.setiawan.anxdre.footballclubmanager

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainMenuTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MatchMenu::class.java)

    @Test
    fun testViewPagerBerhaviour() {
        Thread.sleep(5000)
        onView(withId(R.id.VP_Main)).perform(swipeLeft())
        Thread.sleep(5000)
        onView(withId(R.id.VP_Main)).perform(swipeLeft())
        Thread.sleep(5000)
        onView(withId(R.id.VP_Main)).perform(swipeRight())
        Thread.sleep(5000)
        testRecyclerViewBerhaviour()
    }

    fun testRecyclerViewBerhaviour() {
        Thread.sleep(500)
        onView(allOf(withId(R.id.Rv_EventList), isDisplayed()))
        Thread.sleep(5000)
        onView(allOf(withId(R.id.Rv_EventList), isDisplayed())).perform(swipeUp())
        Thread.sleep(500)
        onView(allOf(withId(R.id.Rv_EventList), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Thread.sleep(5000)
        onView(allOf(withId(R.id.Rv_EventList), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(11, click()))
        Thread.sleep(5000)
        onView(withId(R.id.Fab_AddButton)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.VP_Main)).perform(swipeLeft())
        Thread.sleep(500)
        onView(allOf(withId(R.id.Rv_EventList), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(5000)
        onView(withId(R.id.Fab_AddButton)).perform(click())
        Thread.sleep(500)
        onView(allOf(withId(R.id.Rv_EventList), isDisplayed()))
    }
}