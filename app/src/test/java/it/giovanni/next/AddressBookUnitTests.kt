/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import it.giovanni.next.rubrica.util.AddressBookRetriever
import it.giovanni.next.rubrica.util.ContactUtils
import org.junit.Assert
import org.junit.Test

class AddressBookUnitTests {

    @Test
    fun contactFromNullCursor() {
        Assert.assertTrue(ContactUtils.isEmpty(AddressBookRetriever.cursorToContact(null)))
    }
}