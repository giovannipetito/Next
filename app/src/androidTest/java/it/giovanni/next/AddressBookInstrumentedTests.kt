/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import android.Manifest.permission.READ_CONTACTS
import android.database.MatrixCursor
import android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
import android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import it.giovanni.next.rubrica.util.AddressBookRetriever
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddressBookInstrumentedTests {

    // to execute each task synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // to make read_contacts permission granted while testing
    @get:Rule
    var permissionRule: TestRule = GrantPermissionRule.grant(READ_CONTACTS)

    @Test
    fun listFromNullCursor() {
        Assert.assertEquals(AddressBookRetriever.cursorToContacts(null).size, 0)
    }

    @Test
    fun listFromEmptyCursor() {
        Assert.assertEquals(
            AddressBookRetriever.cursorToContacts(MatrixCursor(emptyArray())).size,
            0
        )
    }

    @Test
    fun listFromFilledCursor() {
        val cursor = MatrixCursor(
            arrayOf(
                DISPLAY_NAME,
                NUMBER
            )
        )
        cursor.newRow()
            .add(DISPLAY_NAME, "Ozzy Osbourne")
            .add(NUMBER, "+66 66 666666")
        val list = AddressBookRetriever.cursorToContacts(cursor)
        Assert.assertEquals(list.size, 1)
        Assert.assertEquals(list[0].first, "Ozzy")
        Assert.assertEquals(list[0].last, "Osbourne")
        Assert.assertEquals(list[0].phone, "+66 66 666666")
    }
}