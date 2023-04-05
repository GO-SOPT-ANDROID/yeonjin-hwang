package org.android.go.sopt.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import org.android.go.sopt.R
import org.android.go.sopt.data.User
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.IntentKey
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.snackBar

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUp()
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener {
            if (isCheckData() && isCheckLength()) {
                setUser()
            } else {
                snackBar(getString(R.string.sign_up_incorrect), binding)
            }
        }
    }

    private fun isCheckData(): Boolean {
        return binding.etId.text.isNotEmpty() && binding.etPw.text.isNotEmpty()
                && binding.etName.text.isNotEmpty() && binding.etFeature.text.isNotEmpty()
    }

    private fun isCheckLength(): Boolean {
        return binding.etId.text.length in 6..10 && binding.etPw.text.length in 8..12
    }

    private fun setUser() {
        val user = User(
            id = binding.etId.text.toString(),
            pw = binding.etPw.text.toString(),
            name = binding.etName.text.toString(),
            feature = binding.etFeature.text.toString()
        )
        val signUpIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
        signUpIntent.putExtra(IntentKey.USER, user)
        setResult(RESULT_OK, signUpIntent)
        if (!isFinishing) {
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        return super.dispatchTouchEvent(ev)
    }
}