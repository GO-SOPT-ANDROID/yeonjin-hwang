package org.android.go.sopt.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

fun snackBar(text: String, binding: ViewBinding) {
    Snackbar.make(
        binding.root,
        text,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}