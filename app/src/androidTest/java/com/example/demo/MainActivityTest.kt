package com.example.demo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.test.runner.AndroidJUnitRunner
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

//    @Test
//    fun changeText_sameActivity() {
//
//        onView(withId(R.id.tv_main)).perform(click())
//
//    }

//    @Test
//    fun function_isCorrect() {
//        assertEquals(XXUtil.getTestStr(), "test")
//
//    }

    @Test
    fun function1_isCorrect() {
//        assertEquals(4, 2 + 2)
        assertEquals(XXUtil.getTestStr1(), "test")

    }

    @Test
    fun function2_isCorrect() {
//        assertEquals(4, 2 + 2)
        assertEquals(XXUtil.getTestStr2(), "test")

    }

}