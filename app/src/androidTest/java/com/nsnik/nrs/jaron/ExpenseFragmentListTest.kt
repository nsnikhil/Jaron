package com.nsnik.nrs.jaron

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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
    fun shouldHaveExpenseList() {
        onView(withId(R.id.expenseFragmentExpenseList)).check(matches(isDisplayed()))
    }


    @Test
    fun shouldDisplayCreateExpenseDialog() {
        onView(withId(R.id.expenseFragmentAddExpense)).perform(click())
        onView(withId(R.id.newExpenseValue)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldAddItemToExpenseList() {
        onView(withId(R.id.expenseFragmentAddExpense)).perform(click())

        onView(withId(R.id.newExpenseValue)).perform(typeText("400"))
        onView(withId(R.id.newExpenseTitle)).perform(typeText("Grocery"))
        onView(withId(R.id.newExpenseDescription)).perform(typeText("Daily Stuff"), closeSoftKeyboard())
        onView(withId(R.id.newExpenseTags)).perform(typeText("#food"), closeSoftKeyboard())

        onView(withId(R.id.newExpenseCreate)).perform(click())
        onView(withId(R.id.newExpenseValue)).check(ViewAssertions.doesNotExist())

        onView(withId(R.id.expenseFragmentExpenseList)).check(matches(hasDescendant(withText("Grocery"))))
    }
}