package com.miso.dermoapp.ui.core.session.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityLogInBinding
import com.miso.dermoapp.domain.models.enumerations.CodeSnackBarCloseAction
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.domain.models.enumerations.TypeSnackBar
import com.miso.dermoapp.ui.core.session.viewModels.LogInViewModel
import com.miso.dermoapp.ui.core.session.viewModels.LogInViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSnackBar
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

        viewModel.errorPassword.observe(this, {
            binding.textViewPasswordError.text = it
        })

        viewModel.editTextPasswordDrawable.observe(this, {
            binding.editTextPassword.setBackgroundResource(it)
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

        viewModel.buttonContinueDrawable.observe(this, {
            binding.buttonLogIn.setBackgroundResource(it)
        })

        viewModel.buttonContinueEnable.observe(this, {
            binding.buttonLogIn.isEnabled = it
        })

        viewModel.navigateToLogIn.observe(this, {
            if (it) {
                loadingDialog.startLoadingDialog()
                if (viewModel.checkOnline(this))
                    viewModel.loginUser()
                else
                    viewModel.snackBarAction.value = 0
            }
        })

        viewModel.snackBarAction.observe(this, {
            loadingDialog.hideLoadingDialog()
            when (it){
                0 -> {
                    viewModel.snackBarNavigate.postValue(CodeSnackBarCloseAction.NONE.code)
                    viewModel.snackBarTextWarning.postValue(getString(R.string.sin_conexion))
                }
            }
        })

        viewModel.snackBarTextWarning.observe(this, {
            CustomSnackBar().showSnackBar(
                it,
                binding.constraintLayout,
                TypeSnackBar.WARNING.code,
                this,
                viewModel.snackBarNavigate.value!!
            )
        })
    }
}