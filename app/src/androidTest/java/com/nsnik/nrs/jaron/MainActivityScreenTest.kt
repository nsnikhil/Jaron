package com.nsnik.nrs.jaron

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.nsnik.nrs.jaron.view.MainActivity
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityScreenTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun shouldDisplayToolbarInView(){
        onView(withId(R.id.mainToolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayCurrentMonthInToolbar(){
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.mainToolbar)))).check(matches(withText("January")))
    }

}