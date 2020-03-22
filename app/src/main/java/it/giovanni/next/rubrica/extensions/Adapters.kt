/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.extensions

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.util.CleanWatcher
import it.giovanni.next.rubrica.view.detail.DetailPresenter
import it.giovanni.next.rubrica.view.master.MasterPresenter
import com.google.android.material.textfield.TextInputEditText

/* these are behaviors we can assign to views, directly into xml files.
   most of them perform operations directly on presenters. */

@BindingAdapter("onOpenFilter")
fun onOpenFilter(view: View, presenter: MasterPresenter) {
    view.setOnClickListener {
        presenter.onOpenFilter()
    }
}

@BindingAdapter("onFilter")
fun onFilter(view: AppCompatEditText, presenter: MasterPresenter) {
    view.addTextChangedListener(object : CleanWatcher() {
        override fun onChanged(string: String) {
            presenter.onFilter(string)
        }
    })
}

@BindingAdapter("onCloseFilter")
fun onCloseFilter(view: View, presenter: MasterPresenter) {
    view.setOnClickListener {
        presenter.onCloseFilter()
        view.hideKeyboard()
    }
}

@BindingAdapter("itemFullName")
fun itemFullName(view: AppCompatTextView, contact: Contact) {
    view.text = contact.first.plus(" ").plus(contact.last)
}

@BindingAdapter("itemPhoneNumber")
fun itemPhoneNumber(view: AppCompatTextView, contact: Contact) {
    view.text = contact.phone
}

@BindingAdapter("onItemClicked", "onPresenter")
fun onItemClicked(view: View, contact: Contact, presenter: MasterPresenter) {
    view.setOnClickListener {
        presenter.performActionClick(contact)
    }
}

@BindingAdapter("onPrimaryAction")
fun onPrimaryAction(view: View, presenter: MasterPresenter) {
    view.setOnClickListener {
        presenter.navigateToAdd()
    }
}

@BindingAdapter("onPrimaryAction")
fun onPrimaryAction(view: View, presenter: DetailPresenter) {
    view.setOnClickListener {
        presenter.onPrimaryAction()
    }
}

@BindingAdapter("onSecondaryAction")
fun onSecondaryAction(view: View, presenter: DetailPresenter) {
    view.setOnClickListener {
        presenter.onSecondaryAction()
    }
}

@BindingAdapter("firstNameValidator")
fun firstNameValidator(view: TextInputEditText, presenter: DetailPresenter) {
    view.addTextChangedListener(object : CleanWatcher() {
        override fun onChanged(string: String) {
            view.error = null
            presenter.onFirst(string)
        }
    })
}

@BindingAdapter("lastNameValidator")
fun lastNameValidator(view: TextInputEditText, presenter: DetailPresenter) {
    view.addTextChangedListener(object : CleanWatcher() {
        override fun onChanged(string: String) {
            view.error = null
            presenter.onLast(string)
        }
    })
}

@BindingAdapter("phoneNumberValidator")
fun phoneNumberValidator(view: TextInputEditText, presenter: DetailPresenter) {
    view.addTextChangedListener(object : CleanWatcher() {
        override fun onChanged(string: String) {
            view.error = null
            presenter.onPhone(string)
        }
    })
}
