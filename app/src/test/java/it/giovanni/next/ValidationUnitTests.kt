/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import it.giovanni.next.rubrica.util.Validators
import org.junit.Assert
import org.junit.Test

class ValidationUnitTests {

    @Test
    fun nullName() {
        Assert.assertFalse(Validators.isValidName(null))
    }

    @Test
    fun emptyName() {
        Assert.assertFalse(Validators.isValidName(""))
    }

    @Test
    fun blankName() {
        Assert.assertFalse(Validators.isValidName(" "))
    }

    @Test
    fun nonBlankName() {
        Assert.assertTrue(Validators.isValidName("Ozzy"))
    }

    @Test
    fun nullPhone() {
        Assert.assertFalse(Validators.isValidPhone(null))
    }

    @Test
    fun emptyPhone() {
        Assert.assertFalse(Validators.isValidPhone(""))
    }

    @Test
    fun blankPhone() {
        Assert.assertFalse(Validators.isValidPhone(" "))
    }

    @Test
    fun nonBlankPhone() {
        Assert.assertFalse(Validators.isValidPhone("A"))
        Assert.assertFalse(Validators.isValidPhone("0"))
        Assert.assertFalse(Validators.isValidPhone("+"))
    }

    @Test
    fun notWellFormattedPhone() {
        Assert.assertFalse(Validators.isValidPhone("+0011666666"))
        Assert.assertFalse(Validators.isValidPhone("+AA BB 123456"))
        Assert.assertFalse(Validators.isValidPhone("00 11 123456"))
        Assert.assertFalse(Validators.isValidPhone("+00 11 12345A"))
        Assert.assertFalse(Validators.isValidPhone("+00 11 12345+"))
        Assert.assertFalse(Validators.isValidPhone("+00 11 123456A"))
        Assert.assertFalse(Validators.isValidPhone("+00 11 123456+"))
        Assert.assertFalse(Validators.isValidPhone("+00 11 12345"))
        Assert.assertFalse(Validators.isValidPhone("+00   11   123456"))
    }

    @Test
    fun wellFormattedPhone() {
        Assert.assertTrue(Validators.isValidPhone("+0 1 123456"))
        Assert.assertTrue(Validators.isValidPhone("+00 11 123456"))
        Assert.assertTrue(Validators.isValidPhone("+1234 1234 1234567890"))
    }
}