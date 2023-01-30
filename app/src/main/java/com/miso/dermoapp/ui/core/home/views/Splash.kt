package com.miso.dermoapp.ui.core.home.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySplashBinding
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModel

class Splash : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(
            this,R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
    }
}