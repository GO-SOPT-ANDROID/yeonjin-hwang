package org.android.go.sopt.util

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

fun Context.snackBar(text: String, binding: ViewBinding) {
    Snackbar.make(
        binding.root,
        text,
        Snackbar.LENGTH_SHORT
    ).show()
}