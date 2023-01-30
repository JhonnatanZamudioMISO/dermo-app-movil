package com.miso.dermoapp.ui.core.home.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySplashBinding
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

class Splash : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DermoApp)
        super.onCreate(savedInstanceState)
        val viewModelFactory = SplashViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
    }
}