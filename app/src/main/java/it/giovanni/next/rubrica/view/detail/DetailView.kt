/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.detail

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import it.giovanni.next.rubrica.data.DetailFragmentMode

interface DetailView {

    fun getLifecycleOwner(): LifecycleOwner

    fun shiftModeTo(mode: DetailFragmentMode)

    fun onFirstText(string: String?)

    fun onLastText(string: String?)

    fun onPhoneText(string: String?)

    fun onFirstError(@StringRes stringRes: Int)

    fun onLastError(@StringRes stringRes: Int)

    fun onPhoneError(@StringRes stringRes: Int)

    fun onRequestPermissions()

    fun navigateTo(where: Int)

    fun navigateBack()
}