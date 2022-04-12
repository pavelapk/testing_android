package ru.pavelapk.testing

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UITest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testPositive() {
        onView(withId(R.id.etInput)).perform(typeText("12,34,56,78,9,0"))
        onView(withId(R.id.btnRun)).perform(click())
        onView(withId(R.id.tvOutput)).check(matches(withText("9")))

        //second click for checking caching from db
        onView(withId(R.id.btnRun)).perform(click())
        onView(withId(R.id.tvOutput)).check(matches(withText("9")))
    }

    @Test
    fun testShortArray() {
        onView(withId(R.id.etInput)).perform(typeText("0"))
        onView(withId(R.id.btnRun)).perform(click())
        onView(withId(R.id.tvOutput)).check(matches(withText("")))
        onView(withId(R.id.tvError)).check(matches(withText("The length of the array must be at least 3")))

        //second click for checking caching from db
        onView(withId(R.id.btnRun)).perform(click())
        onView(withId(R.id.tvOutput)).check(matches(withText("")))
        onView(withId(R.id.tvError)).check(matches(withText("The length of the array must be at least 3")))
    }

    @Test
    fun testNegative() {
        onView(withId(R.id.etInput)).perform(typeText("0,0,0"))
        onView(withId(R.id.btnRun)).perform(click())
        onView(withId(R.id.tvOutput)).check(matches(withText("")))
        onView(withId(R.id.tvError)).check(matches(withText("")))

        //second click for checking caching from db
        onView(withId(R.id.btnRun)).perform(click())
        onView(withId(R.id.tvOutput)).check(matches(withText("")))
        onView(withId(R.id.tvError)).check(matches(withText("")))
    }
}