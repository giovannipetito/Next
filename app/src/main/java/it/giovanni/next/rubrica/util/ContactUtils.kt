/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.util

import it.giovanni.next.rubrica.db.Contact

// utility class, that ease the use of the 'Contact' object

object ContactUtils {

    fun empty(): Contact {
        return Contact(0, "", "", "")
    }

    fun isEmpty(contact: Contact): Boolean {
        return contact.first.isEmpty().and(contact.last.isEmpty()).and(contact.phone.isEmpty())
    }

    // used when importing from address book (display name)
    fun fillFromDisplayName(contact: Contact, displayName: String) {
        val names: List<String> = displayName.trim().split(Regex(" "), 2)
        if (names.isNotEmpty())
            contact.first = names[0].trim()
        if (names.size > 1)
            contact.last = names[1].trim()
    }

    // used when importing from address book (phone number)
    fun fillFromPhoneNumber(contact: Contact, phone: String) {
        contact.phone = phone.trim()
    }
}