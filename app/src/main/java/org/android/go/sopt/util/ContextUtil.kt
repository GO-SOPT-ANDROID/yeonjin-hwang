package org.android.go.sopt.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(text: String, binding: ViewBinding) {
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

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}