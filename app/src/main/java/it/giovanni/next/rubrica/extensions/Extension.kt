/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import it.giovanni.next.rubrica.util.OnceObserver
import com.google.android.material.textfield.TextInputEditText

// these are behaviors we can assign to views

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hideKeyboard() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(windowToken, 0)
}

fun TextInputEditText.editable(boolean: Boolean) {
    isEnabled = boolean
    isClickable = boolean
    isFocusable = boolean
    isFocusableInTouchMode = boolean
}

fun Fragment.goto(where: Int) {
    findNavController().navigate(where)
}

fun Fragment.back() {
    findNavController().popBackStack()
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OnceObserver(handler = onChangeHandler)
    observe(observer, observer)
}