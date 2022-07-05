package com.srdroid.memedb

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.srdroid.memedb.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityEspressoTest {

    private var STRING_TO_BE_TYPED = "drake"

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun load_And_SearchText() {
        // await service call
        Thread.sleep(4000L)
        onView(withId(R.id.menuSearch)).perform(click())
        Thread.sleep(1000L)
        onView(withId(R.id.search_src_text)).perform(typeText(STRING_TO_BE_TYPED))
        Thread.sleep(2000L)
        onView(withId(R.id.search_src_text)).check(matches(withText(STRING_TO_BE_TYPED)))
    }
}