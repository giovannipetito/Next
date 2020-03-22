/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import it.giovanni.next.rubrica.core.App
import it.giovanni.next.rubrica.di.AppModule
import it.giovanni.next.rubrica.di.DaggerAppComponent
import it.giovanni.next.rubrica.di.ModelModule
import it.giovanni.next.rubrica.di.PresenterModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilteringInstrumentedTests {

    @get:Rule
    var testRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java,
        true,
        true
    )

    @Before
    fun before() {
        val instr = InstrumentationRegistry.getInstrumentation()
        val app = instr.targetContext.applicationContext as App
        App.component = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .presenterModule(PresenterModule())
            .modelModule(ModelModule())
            .build()
    }

    @Test
    fun showSearchBar() {
        onView(withId(R.id.filter)).perform(click())
        onView(withId(R.id.edit_filter)).check(matches(isDisplayed()))
        onView(withId(R.id.close)).check(matches(isDisplayed()))
        onView(withId(R.id.close)).perform(click())
        onView(withId(R.id.list_title)).check(matches(isDisplayed()))
        onView(withId(R.id.filter)).check(matches(isDisplayed()))
    }
}