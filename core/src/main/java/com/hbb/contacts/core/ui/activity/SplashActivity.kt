package com.hbb.contacts.core.ui.activity

import android.content.Intent
import android.os.Bundle
import com.hbb.contacts.core.databinding.ActivitySplashBinding
import com.hbb.contacts.core.ui.base.BaseActivity

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.setContentView(binding.root)

        postOnMainThreadDelayed(200L) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            finish()
        }
    }

}