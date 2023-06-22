package org.android.go.sopt.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import org.android.go.sopt.R
import org.android.go.sopt.data.User
import org.android.go.sopt.data.api.ServicePool
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.*
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpService = ServicePool.signUpService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUp()
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener {
            if (isCheckData() && isCheckLength()) {
                completeSignUp()
            } else {
                showSnackBar(getString(R.string.sign_up_incorrect), binding)
            }
        }
    }

    private fun isCheckData(): Boolean {
        return binding.etId.text.isNotBlank() && binding.etPw.text.isNotBlank()
                && binding.etName.text.isNotBlank() && binding.etFeature.text.isNotBlank()
    }

    private fun isCheckLength(): Boolean {
        return binding.etId.text.length in 6..10 && binding.etPw.text.length in 8..12
    }

    private fun completeSignUp() {
        signUpService.signUp(
            with(binding) {
                RequestSignUpDto(
                    etId.text.toString(),
                    etPw.text.toString(),
                    etName.text.toString(),
                    etFeature.text.toString()
                )
            }
        ).enqueueUtil(
            onSuccess = {
                startActivity(
                    Intent(this@SignUpActivity, LoginActivity::class.java)
                )
                showToast(R.string.sign_up_success.toString())
                if (!isFinishing) finish()
            },
            onError = {
                when(it) {
                    304 -> showToast("Not modified")
                    401 -> showToast("Requires authentication")
                    403 -> showToast("Forbidden")
                }
            }
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding){
            etId.clearFocus()
            etPw.clearFocus()
            etName.clearFocus()
            etFeature.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}