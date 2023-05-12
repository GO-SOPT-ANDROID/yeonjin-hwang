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
import org.android.go.sopt.util.IntentKey
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.showSnackBar
import org.android.go.sopt.util.showToast
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
        ).enqueue(object : retrofit2.Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    startActivity(
                        Intent(this@SignUpActivity, LoginActivity::class.java)
                    )
                    response.body()?.message?.let { showToast(it) } ?: "회원가입 성공"
                    if (!isFinishing) finish()
                } else {
                    response.body()?.message?.let { showToast(it) } ?: "서버통신 실패"
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                t.message?.let { showToast(it) } ?: "서버통신 살패"
            }
        })
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