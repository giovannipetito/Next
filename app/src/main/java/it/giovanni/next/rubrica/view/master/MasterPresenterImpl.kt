/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.master

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import it.giovanni.next.R
import it.giovanni.next.rubrica.data.MasterFragmentMode
import it.giovanni.next.rubrica.data.MasterFragmentMode.ADDRESS_BOOK
import it.giovanni.next.rubrica.data.MasterFragmentMode.ROOM
import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.model.Model
import it.giovanni.next.rubrica.util.ContactUtils
import it.giovanni.next.rubrica.util.ListFiltering
import javax.inject.Inject

class MasterPresenterImpl @Inject constructor(private val model: Model) :
    MasterPresenter {

    private lateinit var view: MasterView
    private lateinit var mode: MasterFragmentMode
    private var items: MutableLiveData<List<Contact>> = MutableLiveData(emptyList())

    override fun init(view: MasterView, mode: MasterFragmentMode) {
        this.view = view
        this.mode = mode
        when (mode) {
            ROOM -> {
                view.setButtonAddVisible(true)
                view.setTitle(R.string.app_name)
                view.setBackNavigationFeatureEnabled(false)
                model.room.contacts().getAll().observe(view.getLifecycleOwner(), Observer {
                    items.value = it ?: emptyList()
                    view.onFullList(items.value ?: emptyList())
                })
            }
            ADDRESS_BOOK -> {
                view.setButtonAddVisible(false)
                view.setTitle(R.string.action_import)
                view.setBackNavigationFeatureEnabled(true)
                model.addressBook.observe(view.getLifecycleOwner(), Observer {
                    items.value = it ?: emptyList()
                    view.onFullList(items.value ?: emptyList())
                })
            }
        }
        items.observe(view.getLifecycleOwner(), Observer {
            view.updateListDependentViews(it.isEmpty())
        })
    }

    override fun performActionClick(contact: Contact) {
        if (!ContactUtils.isEmpty(model.operating.value ?: ContactUtils.empty()))
            return
        model.operating.value = contact
        when (mode) {
            ROOM -> view.navigateTo(R.id.to_show_entry)
            ADDRESS_BOOK -> view.navigateTo(R.id.to_edit_entry)
        }
    }

    override fun onOpenFilter() {
        if ((items.value ?: emptyList()).isEmpty())
            return
        view.setTitleBarVisible(false)
        view.setSearchBarVisible(true)
    }

    override fun onCloseFilter() {
        view.setTitleBarVisible(true)
        view.setSearchBarVisible(false)
        view.onFilteredList(items.value ?: emptyList())
    }

    override fun onFilter(string: String) {
        view.onFilteredList(ListFiltering.filterList(items.value ?: emptyList(), string))
    }

    override fun navigateToAdd() {
        model.clearOperating()
        view.navigateTo(R.id.to_add_entry)
    }
}