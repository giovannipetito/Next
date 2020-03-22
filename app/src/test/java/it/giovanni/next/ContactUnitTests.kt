/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.util.ContactUtils
import org.junit.Assert
import org.junit.Test

class ContactUnitTests {

    @Test
    fun isEmpty() {
        val contact: Contact = ContactUtils.empty()
        Assert.assertNotNull(contact)
        Assert.assertEquals(contact.id, 0)
        Assert.assertEquals(contact.first, "")
        Assert.assertEquals(contact.last, "")
        Assert.assertEquals(contact.phone, "")
    }

    @Test
    fun fromEmptyDisplayName() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromDisplayName(contact, "")
        Assert.assertTrue(contact.first.isEmpty())
        Assert.assertTrue(contact.last.isEmpty())
    }

    @Test
    fun fromBlankDisplayName() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromDisplayName(contact, "   ")
        Assert.assertTrue(contact.first.isEmpty())
        Assert.assertTrue(contact.last.isEmpty())
    }

    @Test
    fun fromSingleSentenceDisplayName() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromDisplayName(contact, "Ozzy")
        Assert.assertEquals(contact.first, "Ozzy")
        Assert.assertTrue(contact.last.isEmpty())
    }

    @Test
    fun fromDoubleSentenceDisplayName() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromDisplayName(contact, "Ozzy Osbourne")
        Assert.assertEquals(contact.first, "Ozzy")
        Assert.assertEquals(contact.last, "Osbourne")
    }

    @Test
    fun fromTripleSentenceDisplayName() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromDisplayName(contact, "Ozzy Osbourne Jr")
        Assert.assertEquals(contact.first, "Ozzy")
        Assert.assertEquals(contact.last, "Osbourne Jr")
    }

    @Test
    fun fromMultiSpacedSentencesDisplayName() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromDisplayName(contact, "Ozzy   Osbourne")
        Assert.assertEquals(contact.first, "Ozzy")
        Assert.assertEquals(contact.last, "Osbourne")
    }

    @Test
    fun fromEmptyPhoneNumber() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromPhoneNumber(contact, "")
        Assert.assertTrue(contact.phone.isEmpty())
    }

    @Test
    fun fromBlankPhoneNumber() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromPhoneNumber(contact, " ")
        Assert.assertTrue(contact.phone.isEmpty())
    }

    @Test
    fun fromNotBlankPhoneNumber() {
        val contact: Contact = ContactUtils.empty()
        ContactUtils.fillFromPhoneNumber(contact, "+66 66 666666")
        Assert.assertEquals(contact.phone, "+66 66 666666")
    }
}