/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.util

import it.giovanni.next.rubrica.db.Contact
import java.util.*

// utility class that, given a list of contacts and a string, filters that list

object ListFiltering {

    // logic here is as follow: if string is contained into almost one between first, last, or phone
    fun filterList(original: List<Contact>, string: String): List<Contact> {
        if (original.isEmpty() || string.isEmpty())
            return original
        return original.filter {
            it.first.trim().toLowerCase(Locale.getDefault()).contains(
                string.trim().toLowerCase(
                    Locale.getDefault()
                )
            ) || it.last.trim().toLowerCase(Locale.getDefault()).contains(
                string.trim().toLowerCase(
                    Locale.getDefault()
                )
            ) || it.phone.trim().toLowerCase(Locale.getDefault()).contains(
                string.trim().toLowerCase(
                    Locale.getDefault()
                )
            )
        }
    }
}