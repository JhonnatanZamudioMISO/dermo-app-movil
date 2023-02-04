package com.miso.dermoapp.ui.core.session.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySignUpBinding
import com.miso.dermoapp.domain.models.enumerations.CodeSnackBarCloseAction
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.domain.models.enumerations.TypeSnackBar
import com.miso.dermoapp.ui.core.home.views.Welcome
import com.miso.dermoapp.ui.core.session.viewModels.SignUpViewModel
import com.miso.dermoapp.ui.core.session.viewModels.SignUpViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSnackBar
import com.miso.dermoapp.ui.core.utils.LoadingDialog
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
        val loadingDialog = LoadingDialog(this, getString(R.string.creaandoTuCuenta))

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }

        binding.checkboxMeat.setOnClickListener {
            if (it is CheckBox) {
                viewModel.setTerms(it.isChecked)
            }
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

        viewModel.editTextPasswordConfirmDrawable.observe(this, {
            binding.editTextPasswordConfirm.setBackgroundResource(it)
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
            if (it)
                loadingDialog.startLoadingDialog()
            if (viewModel.checkOnline(this))
                //viewModel.searchUser()
            else {
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(
            this@SignUp,
            Welcome::class.java
        )
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

    private fun goToWelcome() {
        val intent = Intent(this@SignUp, Welcome::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        finish()
    }
}