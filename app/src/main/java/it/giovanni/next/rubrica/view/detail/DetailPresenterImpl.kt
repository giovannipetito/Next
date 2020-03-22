/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.detail

import it.giovanni.next.R
import it.giovanni.next.rubrica.data.DetailFragmentMode
import it.giovanni.next.rubrica.model.Model
import it.giovanni.next.rubrica.util.Validators
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailPresenterImpl @Inject constructor(val model: Model) :
    DetailPresenter {

    private lateinit var view: DetailView
    private lateinit var mode: DetailFragmentMode

    override fun init(view: DetailView, mode: DetailFragmentMode) {
        this.view = view
        this.mode = mode
        view.onFirstText(model.operating.value?.first)
        view.onLastText(model.operating.value?.last)
        view.onPhoneText(model.operating.value?.phone)
        view.shiftModeTo(mode)
    }

    override fun onFirst(string: String) {
        model.operating.value?.first = string
    }

    override fun onLast(string: String) {
        model.operating.value?.last = string
    }

    override fun onPhone(string: String) {
        model.operating.value?.phone = string
    }

    override fun validateFirst(): Boolean {
        Validators.validateName(model.operating.value?.first).let {
            view.onFirstError(it)
            return it == 0
        }
    }

    override fun validateLast(): Boolean {
        Validators.validateName(model.operating.value?.last).let {
            view.onLastError(it)
            return it == 0
        }
    }

    override fun validatePhone(): Boolean {
        Validators.validatePhone(model.operating.value?.phone).let {
            view.onPhoneError(it)
            return it == 0
        }
    }

    override fun onPrimaryAction() {
        when (mode) {
            DetailFragmentMode.SHOW_CONTACT -> {
                mode = DetailFragmentMode.EDIT_CONTACT
                view.shiftModeTo(mode)
            }
            else -> {
                validateFirst().and(validateLast()).and(validatePhone()).let {
                    if (it.not())
                        return
                    val contact = model.clearOperating()
                    GlobalScope.launch {
                        model.room.contacts().insertOrUpdate(contact)
                        view.navigateBack()
                    }
                }
            }
        }
    }

    override fun onSecondaryAction() {
        view.onRequestPermissions()
    }

    override fun onRequestPermissionsResult(boolean: Boolean) {
        if (boolean.not())
            return
        GlobalScope.launch(Dispatchers.Main) {
            model.getAddressBookData()
        }.invokeOnCompletion {
            model.clearOperating()
            view.navigateTo(R.id.fragment_list_import)
        }
    }

    override fun onNavigatingBack() {
        model.clearOperating()
    }
}