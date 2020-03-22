/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import android.Manifest.permission.READ_CONTACTS
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import it.giovanni.next.rubrica.model.Model
import it.giovanni.next.rubrica.util.ContactUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ModelInstrumentedTests {

    private lateinit var model: Model

    // to execute each task synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // to make read_contacts permission granted while testing
    @get:Rule
    var permissionRule: TestRule = GrantPermissionRule.grant(READ_CONTACTS)

    @Before
    fun before() {
        model = Model(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun hasNotNullContents() {
        Assert.assertNotNull(model.room)
        Assert.assertNotNull(model.operating)
        Assert.assertNotNull(model.addressBook)
    }

    @Test
    fun operatingGetsCorrectlyCleared() {
        model.operating.value!!.first = "Ozzy"
        model.operating.value!!.last = "Osbourne"
        model.operating.value!!.phone = "+66 66 666666"
        Assert.assertFalse(ContactUtils.isEmpty(model.operating.value!!))
        model.clearOperating()
        Assert.assertTrue(ContactUtils.isEmpty(model.operating.value!!))
    }

    @Test
    fun addressBookGetsCorrectlyFilled() {
        model.getAddressBookData()
        Assert.assertNotEquals(model.addressBook.value!!.size, 0)
    }
}