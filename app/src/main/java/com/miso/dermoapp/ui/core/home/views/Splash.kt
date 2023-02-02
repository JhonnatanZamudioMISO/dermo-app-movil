package com.miso.dermoapp.ui.core.home.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySplashBinding
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        viewModel.loading.observe(this, {
            with(binding) {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (it.equals(true)) {
                        startLoading(imageViewLoading)
                    } else {
                        stopLoading(imageViewLoading)
                    }
                }
            }
        })
    }

    private fun stopLoading(imageViewLoading: ImageView) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.invisible)
        imageViewLoading.startAnimation(animation)
    }

    private fun startLoading(imageViewLoading: ImageView) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.loading)
        imageViewLoading.startAnimation(animation)
    }
}