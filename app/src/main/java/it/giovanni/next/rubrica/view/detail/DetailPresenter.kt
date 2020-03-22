/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.detail

import it.giovanni.next.rubrica.data.DetailFragmentMode

interface DetailPresenter {

    fun init(view: DetailView, mode: DetailFragmentMode)

    fun onFirst(string: String)

    fun onLast(string: String)

    fun onPhone(string: String)

    fun validateFirst(): Boolean

    fun validateLast(): Boolean

    fun validatePhone(): Boolean

    fun onPrimaryAction()

    fun onSecondaryAction()

    fun onRequestPermissionsResult(boolean: Boolean)

    fun onNavigatingBack()
}