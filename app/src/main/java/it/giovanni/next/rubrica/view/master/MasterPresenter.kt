/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.master

import it.giovanni.next.rubrica.data.MasterFragmentMode
import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.view.master.MasterView

interface MasterPresenter {

    fun init(view: MasterView, mode: MasterFragmentMode)

    fun performActionClick(contact: Contact)

    fun onOpenFilter()

    fun onCloseFilter()

    fun onFilter(string: String)

    fun navigateToAdd()
}