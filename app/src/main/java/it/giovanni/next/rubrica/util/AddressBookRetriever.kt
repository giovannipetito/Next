/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.util

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract.CommonDataKinds.Phone.*
import it.giovanni.next.rubrica.db.Contact

/* utility class to get rid of address book entries
   code here is very atomized, 'cause tests will be more clear and simple to write*/

object AddressBookRetriever {

    fun getAddressBookData(context: Context): List<Contact> {
        return cursorToContacts(
            context.contentResolver.query(
                CONTENT_URI,
                null,
                null,
                null,
                DISPLAY_NAME + " ASC"
            )
        )
    }

    fun cursorToContacts(c: Cursor?): List<Contact> {
        val contacts: ArrayList<Contact> = arrayListOf()
        c?.let {
            while (it.moveToNext()) {
                val contact = cursorToContact(it)
                if (ContactUtils.isEmpty(contact).not())
                    contacts.add(contact)
            }
            it.close()
        }
        return contacts
    }

    fun cursorToContact(c: Cursor?): Contact {
        val contact: Contact = ContactUtils.empty()
        c?.let {
            ContactUtils.fillFromDisplayName(contact, it.getString(it.getColumnIndex(DISPLAY_NAME)))
            ContactUtils.fillFromPhoneNumber(contact, it.getString(it.getColumnIndex(NUMBER)))
        }
        return contact
    }
}