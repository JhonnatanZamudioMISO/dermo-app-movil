package com.miso.dermoapp.ui.core.session.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySignUpBinding
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.ui.core.home.views.Welcome
import com.miso.dermoapp.ui.core.session.viewModels.SignUpViewModel
import com.miso.dermoapp.ui.core.session.viewModels.SignUpViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

@Suppress("DEPRECATION", "COMPATIBILITY_WARNING")
class SignUp : AppCompatActivity() {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: ActivitySignUpBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResponseErrorField.initialize(applicationContext)
        val viewModelFactory = SignUpViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[SignUpViewModel::class.java]
        binding = setContentView(this, R.layout.activity_sign_up)
        binding.lifecycleOwner = this
        binding.vModel = viewModel

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.errorEmail.observe(this, {
            binding.textViewEmailError.text = it
        })

        viewModel.errorPassword.observe(this, {
            binding.textViewPasswordError.text = it
        })

        viewModel.errorPasswordConfirm.observe(this, {
            binding.textViewPasswordConfirmError.text = it
        })

        viewModel.buttonContinueDrawable.observe(this, {
            binding.buttonContinue.setBackgroundResource(it)
        })

        viewModel.editTextEmailDrawable.observe(this, {
            binding.editTextEmail.setBackgroundResource(it)
        })

        viewModel.editTextPasswordDrawable.observe(this, {
            binding.editTextPassword.setBackgroundResource(it)
        })

        viewModel.buttonContinueEnable.observe(this, {
            binding.buttonContinue.isEnabled = it
        })

        viewModel.showPassword.observe(this, {
            if (it){
                binding.editTextPassword.transformationMethod =
                    PasswordTransformationMethod()
                binding.imageViewShow.setBackgroundResource(R.drawable.ic_eye_line)
            } else {
                binding.editTextPassword.transformationMethod = null
                binding.imageViewShow.setBackgroundResource(R.drawable.ic_eye)
            }
            binding.editTextPassword.setSelection(binding.editTextPassword.length())
        })

        viewModel.showPasswordConfirm.observe(this, {
            if (it){
                binding.editTextPasswordConfirm.transformationMethod =
                    PasswordTransformationMethod()
                binding.imageViewShowConfirm.setBackgroundResource(R.drawable.ic_eye_line)
            } else {
                binding.editTextPasswordConfirm.transformationMethod = null
                binding.imageViewShowConfirm.setBackgroundResource(R.drawable.ic_eye)
            }
            binding.editTextPasswordConfirm.setSelection(binding.editTextPasswordConfirm.length())
        })

        viewModel.navigateToLogIn.observe(this, {
            if (it == true)
                goToWelcome()
        })
    }

    private fun goToWelcome() {
        val intent = Intent(this@SignUp, Welcome::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        finish()
    }
}