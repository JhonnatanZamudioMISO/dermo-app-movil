package com.miso.dermoapp.ui.core.home.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityWelcomeBinding
import com.miso.dermoapp.ui.core.home.viewModels.WelcomeViewModel
import com.miso.dermoapp.ui.core.home.viewModels.WelcomeViewModelFactory
import com.miso.dermoapp.ui.core.session.views.LogIn
import com.miso.dermoapp.ui.core.session.views.SignUp
import kotlinx.coroutines.DelicateCoroutinesApi

class Welcome : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel
    private lateinit var binding: ActivityWelcomeBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = WelcomeViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[WelcomeViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        viewModel.navigateToSignUp.observe(this,{
            if (it)
                goToChangeScreen(Intent(this@Welcome, SignUp::class.java))
        })
        viewModel.navigateToLogIn.observe(this,{
            if (it)
                goToChangeScreen(Intent(this@Welcome, LogIn::class.java))
        })
    }

    private fun goToChangeScreen(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }
}