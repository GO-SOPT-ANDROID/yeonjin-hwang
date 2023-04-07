package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import org.android.go.sopt.presentation.home.ProfileActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.User
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.util.IntentKey
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.snackBar

class LoginActivity : AppCompatActivity() {

    private var user: User? = null
    private lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()
        goSignUpPage()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            snackBar(getString(R.string.sign_up_done), binding)
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

    private fun login() {
        binding.btnLogin.setOnClickListener {
            if (isCheckData()) {
                setLoginData()
            } else {
                snackBar(getString(R.string.sign_in_fail), binding)
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

    private fun setLoginData() {
        if (isCorrect()) {
            snackBar(getString(R.string.sign_in_done), binding)
            val loginIntent = Intent(this, ProfileActivity::class.java)
            loginIntent.putExtra(IntentKey.USER, user)
            startActivity(loginIntent)
            finish()
        } else {
            snackBar(getString(R.string.login_incorrect), binding)
        }
    }

    private fun isCorrect(): Boolean {
        return binding.etId.text.toString() == user?.id && binding.etPw.text.toString() == user?.pw
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        return super.dispatchTouchEvent(ev)
    }
}