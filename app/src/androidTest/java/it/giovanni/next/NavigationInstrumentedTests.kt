/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import android.Manifest.permission.READ_CONTACTS
import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.davethebrave.talkalot.adapter.ViewHolderGeneral
import com.davethebrave.talkalot.core.App
import com.davethebrave.talkalot.core.HostActivity
import com.davethebrave.talkalot.data.DetailFragmentMode
import com.davethebrave.talkalot.data.DetailFragmentMode.*
import com.davethebrave.talkalot.data.MasterFragmentMode
import com.davethebrave.talkalot.data.MasterFragmentMode.ROOM
import com.davethebrave.talkalot.di.AppModule
import com.davethebrave.talkalot.di.DaggerAppComponent
import com.davethebrave.talkalot.di.ModelModule
import com.davethebrave.talkalot.di.PresenterModule
import com.davethebrave.talkalot.view.detail.DetailFragment
import com.davethebrave.talkalot.view.master.MasterFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationInstrumentedTests {

    private lateinit var res: Resources
    private lateinit var master: MasterFragment

    @get:Rule
    var testRule: ActivityTestRule<HostActivity> = ActivityTestRule(
        HostActivity::class.java,
        true,
        true
    )

    // to make read_contacts permission granted while testing
    @get:Rule
    var permissionRule: TestRule = GrantPermissionRule.grant(READ_CONTACTS)

    @Before
    fun before() {
        res = testRule.activity.resources
        val instr = InstrumentationRegistry.getInstrumentation()
        val app = instr.targetContext.applicationContext as App
        App.component = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .presenterModule(PresenterModule())
            .modelModule(ModelModule())
            .build()
        master = getLastFragment() as MasterFragment
        App.component.inject(master)
        Assert.assertEquals(getMode(master), ROOM)
    }

    @Test
    fun goToDetail() {
        onView(withId(R.id.recycler)).perform(actionOnItemAtPosition<ViewHolderGeneral>(0, click()))
        val detail = getLastFragment() as DetailFragment
        App.component.inject(detail)
        Assert.assertEquals(getMode(detail), SHOW_CONTACT)
    }

    @Test
    fun goToEditable() {
        onView(withId(R.id.recycler)).perform(actionOnItemAtPosition<ViewHolderGeneral>(0, click()))
        val detail = getLastFragment() as DetailFragment
        App.component.inject(detail)
        Assert.assertEquals(getMode(detail), SHOW_CONTACT)
        onView(withId(R.id.action_primary)).perform(click())
        Assert.assertEquals(getMode(detail), EDIT_CONTACT)
    }

    @Test
    fun goToAdd() {
        onView(withId(R.id.action_add)).perform(click())
        val detail = getLastFragment() as DetailFragment
        App.component.inject(detail)
        Assert.assertEquals(getMode(detail), ADD_CONTACT)
    }

    private fun getLastFragment(): Fragment {
        val host: NavHostFragment = testRule
            .activity
            .supportFragmentManager
            .findFragmentById(R.id.navigation_host)!! as NavHostFragment
        return host
            .childFragmentManager
            .fragments[host
            .childFragmentManager
            .fragments
            .size - 1]
    }

    private fun getMode(master: MasterFragment): MasterFragmentMode {
        return master.arguments?.getSerializable("mode") as MasterFragmentMode
    }

    private fun getMode(detail: DetailFragment): DetailFragmentMode {
        return detail.arguments?.getSerializable("mode") as DetailFragmentMode
    }
}