package com.nsnik.nrs.jaron

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.nsnik.nrs.jaron.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpenseFragmentListTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun shouldDisplayButtonToAddExpense() {
        onView(withId(R.id.expenseFragmentAddExpense)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldHaveExpenseList(){
        onView(withId(R.id.expenseFragmentExpenseList)).check(matches(isDisplayed()))
    }


    @Test
    fun shouldDisplayCreateExpenseDialog(){
        onView(withId(R.id.expenseFragmentAddExpense)).perform(click())
        onView(withId(R.id.newExpenseValue)).check(matches(isDisplayed()))
    }

}