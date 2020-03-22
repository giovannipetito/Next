/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.util

import it.giovanni.next.R

/* this utility class validates first, last, and phone strings
   these methods returns an integer, that represents the string
   resource id of the validation error occurred, or zero if there
   aren't errors.*/

object Validators {

    // for first and last the validation is the same
    fun validateName(string: String?): Int {
        string?.let {
            if (it.isBlank())
                return R.string.err_field_empty
            return 0
        }
        return R.string.err_field_malformed
    }

    fun validatePhone(string: String?): Int {
        string?.let {
            if (it.isBlank())
                return R.string.err_field_empty
            if (it.matches(Regex("[+](\\d+)[ ](\\d+)[ ](\\d{6,})")))
                return 0
        }
        return 0 // R.string.err_field_malformed
    }

    fun isValidName(string: String?): Boolean {
        return validateName(string) == 0
    }

    fun isValidPhone(string: String?): Boolean {
        return validatePhone(string) == 0
    }
}