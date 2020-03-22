/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.master

import androidx.lifecycle.LifecycleOwner
import it.giovanni.next.rubrica.db.Contact

interface MasterView {

    fun getLifecycleOwner(): LifecycleOwner

    fun onFullList(contacts: List<Contact>)

    fun onFilteredList(contacts: List<Contact>)

    fun setBackNavigationFeatureEnabled(enabled: Boolean)

    fun setTitle(res: Int)

    fun setTitleBarVisible(visible: Boolean)

    fun setSearchBarVisible(visible: Boolean)

    fun setButtonAddVisible(visible: Boolean)

    fun updateListDependentViews(listIsEmpty: Boolean)

    fun navigateTo(where: Int)

    fun navigateBack()
}