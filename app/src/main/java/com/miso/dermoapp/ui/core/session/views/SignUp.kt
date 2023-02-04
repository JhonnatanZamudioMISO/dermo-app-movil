package com.miso.dermoapp.ui.core.session.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySignUpBinding
import com.miso.dermoapp.ui.core.session.viewModels.SignUpViewModel
import com.miso.dermoapp.ui.core.session.viewModels.SignUpViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("DEPRECATION")
class SignUp : AppCompatActivity() {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: ActivitySignUpBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = SignUpViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[SignUpViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.lifecycleOwner = this
        binding.vModel = viewModel

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }
}