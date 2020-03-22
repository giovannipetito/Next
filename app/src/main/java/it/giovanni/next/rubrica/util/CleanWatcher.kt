/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.util

import android.text.Editable
import android.text.TextWatcher

// simple 'OnTextChangedListener' class, that gets rid of boilerplate code

abstract class CleanWatcher : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // empty
    }

    override fun afterTextChanged(p0: Editable?) {
        // empty
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onChanged(p0.toString())
    }

    // this is the only method we'll use externally
    abstract fun onChanged(string: String)
}