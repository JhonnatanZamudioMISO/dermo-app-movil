package com.miso.dermoapp.ui.core.session.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityLogInBinding
import com.miso.dermoapp.domain.models.enumerations.*
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import com.miso.dermoapp.ui.core.home.views.Welcome
import com.miso.dermoapp.ui.core.profile.views.UserDematologicalProfile
import com.miso.dermoapp.ui.core.profile.views.UserProfile
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

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }

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

        viewModel.navigateToSignUp.observe(this, {
            if (it)
                goToScreen(Intent(this@LogIn,SignUp::class.java))
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

        viewModel.resultLoginUser.observe(this, {
            when(it){
                CodeResponseLoginUser.INICIO_DE_SESION_EXITOSO.code-> loadingDialog.succesful(R.string.credencialesValidas)
                CodeResponseLoginUser.ERROR.code -> loadingDialog.error()
                CodeResponseLoginUser.LA_CUENTA_NO_EXISTE.code -> loadingDialog.warning(getString(R.string.noExisteUnaCuenta))
                CodeResponseLoginUser.CREDENCIALES_INVALIDAS.code -> loadingDialog.warning(getString(R.string.credencialesInvalidas))
            }
            viewModel.delayScreen(it)
        })

        viewModel.validateChangeScreen.observe(this, {
            when(it) {
                CodeResponseLoginUser.INICIO_DE_SESION_EXITOSO.code -> viewModel.validateStatusProfile(this)
                CodeResponseLoginUser.ERROR.code -> loadingDialog.hideLoadingDialog()
                CodeResponseLoginUser.LA_CUENTA_NO_EXISTE.code -> goToScreen(
                    Intent(
                        this@LogIn,
                        SignUp::class.java
                    )
                )
                CodeResponseLoginUser.CREDENCIALES_INVALIDAS.code -> {
                    loadingDialog.hideLoadingDialog()
                    binding.editTextPassword.setText("")
                }
                CodeResponseLoginUser.PERFIL_DE_USUARIO.code -> {
                    goToScreen(
                        Intent(
                            this@LogIn,
                            UserProfile::class.java
                        )
                    )
                    sharedPreferences().set(this, KeySharedPreferences.STATUS_PROFILE.value, CodeResponseLoginUser.PERFIL_DE_USUARIO.code.toString())
                }
                CodeResponseLoginUser.PERFIL_DERMATOLOGICO.code -> {
                    goToScreen(
                        Intent(
                            this@LogIn,
                            UserDematologicalProfile::class.java
                        )
                    )
                    sharedPreferences().set(this, KeySharedPreferences.STATUS_PROFILE.value, CodeResponseLoginUser.PERFIL_DERMATOLOGICO.code.toString())
                }
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(
            this@LogIn,
            Welcome::class.java
        )
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

    private fun goToScreen(intent: Intent) {
        loadingDialog.hideLoadingDialog()
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        finish()
    }
}