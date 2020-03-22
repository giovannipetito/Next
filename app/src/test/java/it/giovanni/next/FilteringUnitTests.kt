/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.util.ContactUtils
import it.giovanni.next.rubrica.util.ListFiltering
import org.junit.Assert
import org.junit.Test

class FilteringUnitTests {

    private fun getEmptyList(): List<Contact> {
        return emptyList()
    }

    private fun getListWithEmptyItems(): List<Contact> {
        return arrayListOf(
            ContactUtils.empty(),
            ContactUtils.empty(),
            ContactUtils.empty()
        )
    }

    private fun getWellComposedList(): List<Contact> {
        return arrayListOf(
            Contact(0, "Ozzy", "Osbourne", "+66 66 666666"),
            Contact(1, "Arnold", "Schwarzenegger", "+12 34 567890"),
            Contact(2, "Lapo", "Elkann", "+12 34 567890")
        )
    }

    @Test
    fun filterWithEmptyString() {
        Assert.assertEquals(ListFiltering.filterList(getEmptyList(), "").size, 0)
        Assert.assertEquals(ListFiltering.filterList(getListWithEmptyItems(), "").size, 3)
        Assert.assertEquals(ListFiltering.filterList(getWellComposedList(), "").size, 3)
    }

    @Test
    fun filterWithBlankString() {
        Assert.assertEquals(ListFiltering.filterList(getEmptyList(), " ").size, 0)
        Assert.assertEquals(ListFiltering.filterList(getListWithEmptyItems(), " ").size, 3)
        Assert.assertEquals(ListFiltering.filterList(getWellComposedList(), " ").size, 3)
    }

    @Test
    fun filterWithNonBlankString() {
        Assert.assertEquals(ListFiltering.filterList(getEmptyList(), "Arnold").size, 0)
        Assert.assertEquals(ListFiltering.filterList(getListWithEmptyItems(), "Arnold").size, 0)
        Assert.assertEquals(ListFiltering.filterList(getWellComposedList(), "Arnold").size, 1)
        Assert.assertEquals(ListFiltering.filterList(getWellComposedList(), "34 5").size, 2)
        Assert.assertEquals(ListFiltering.filterList(getWellComposedList(), "   Ozzy   ").size, 1)
        Assert.assertEquals(ListFiltering.filterList(getWellComposedList(), "Oz zy").size, 0)
    }
}