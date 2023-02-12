package com.miso.dermoapp.ui.core.session.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityLogInBinding
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.ui.core.session.viewModels.LogInViewModel
import com.miso.dermoapp.ui.core.session.viewModels.LogInViewModelFactory
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class LogIn : AppCompatActivity() {
    private lateinit var viewModel: LogInViewModel
    private lateinit var binding: ActivityLogInBinding
    private lateinit var loadingDialog: LoadingDialog
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResponseErrorField.initialize(applicationContext)
        val viewModelFactory = LogInViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[LogInViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.validandoTusCredenciales))

        viewModel.errorEmail.observe(this, {
            binding.textViewEmailError.text = it
        })

        viewModel.editTextEmailDrawable.observe(this, {
            binding.editTextEmail.setBackgroundResource(it)
        })

    }
}