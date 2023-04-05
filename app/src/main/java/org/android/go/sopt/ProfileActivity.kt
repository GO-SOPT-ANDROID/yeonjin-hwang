package org.android.go.sopt

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import org.android.go.sopt.data.User
import org.android.go.sopt.databinding.ActivityProfileBinding
import org.android.go.sopt.util.IntentKey

class ProfileActivity : AppCompatActivity() {

    private var user: User? = null
    private lateinit var binding: ActivityProfileBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initData() {
        user = intent.getParcelableExtra(IntentKey.USER, User::class.java)
        bindData()
    }

    private fun bindData() {
        with(binding) {
            tvName.text = getString(R.string.profile_name_format, user?.name)
            tvFeature.text = getString(R.string.profile_feature_format, user?.feature)
        }
    }
}