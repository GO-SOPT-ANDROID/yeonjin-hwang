package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import org.android.go.sopt.MainActivity
import org.android.go.sopt.presentation.home.ProfileActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.User
import org.android.go.sopt.data.api.ServicePool
import org.android.go.sopt.data.remote.model.RequestLoginDto
import org.android.go.sopt.data.remote.model.ResponseLoginDto
import org.android.go.sopt.data.remote.service.LoginService
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.util.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity() {

    private var user: User? = null
    private lateinit var binding: ActivityLoginBinding
    private var loginService = ServicePool.loginService

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLoginEvent()
        goSignUpPage()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            showSnackBar(getString(R.string.sign_up_done), binding)
            user = it.data?.getParcelableExtra(IntentKey.USER, User::class.java)
            bindData()
        }
    }

    private fun bindData() {
        with(binding) {
            etId.setText(user?.id ?: "")
            etPw.setText(user?.pw ?: "")
        }
    }

    private fun initLoginEvent() {
        binding.btnLogin.setOnClickListener {
            if (isCheckData()) {
                completeLogin()
            } else {
                showSnackBar(getString(R.string.sign_in_fail), binding)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun goSignUpPage() {
        binding.btnSignup.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            activityResultLauncher.launch(signUpIntent)
        }
    }

    private fun isCheckData(): Boolean {
        return binding.etId.text.isNotEmpty() && binding.etPw.text.isNotEmpty()
    }

    private fun completeLogin() {
        loginService.login(
            with(binding) {
                RequestLoginDto(
                    etId.text.toString(),
                    etPw.text.toString()
                )
            }
        ).enqueueUtil(
            onSuccess = {
                startActivity(
                    Intent(this@LoginActivity, MainActivity::class.java)
                )
                showToast(R.string.sign_in_done.toString())
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
        with(binding) {
            etId.clearFocus()
            etPw.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}