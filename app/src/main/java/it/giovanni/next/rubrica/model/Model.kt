/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.util.AddressBookRetriever
import it.giovanni.next.rubrica.util.ContactUtils
import it.giovanni.next.rubrica.db.AppDatabase
import javax.inject.Inject

data class Model @Inject constructor(private val context: Context) {

    // a room database
    val room: AppDatabase = AppDatabase.getInstance(context)

    // a 'Contact' item, that this app stores as the 'clicked' or 'in use' item when navigating to details
    var operating: MutableLiveData<Contact> = MutableLiveData(ContactUtils.empty())

    // an address book list of 'Contact' items
    val addressBook: MutableLiveData<List<Contact>> = MutableLiveData(emptyList())

    // cleanup method, used when navigating back to master fragment(s)
    fun clearOperating(): Contact {
        val contact: Contact = operating.value as Contact
        operating.value = ContactUtils.empty()
        return contact
    }

    // address book retrieval method
    fun getAddressBookData() {
        addressBook.value = AddressBookRetriever.getAddressBookData(context)
    }
}