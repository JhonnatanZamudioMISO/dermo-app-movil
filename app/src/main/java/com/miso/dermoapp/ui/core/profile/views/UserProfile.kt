package com.miso.dermoapp.ui.core.profile.views

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityUserProfileBinding
import com.miso.dermoapp.domain.models.enumerations.*
import com.miso.dermoapp.ui.core.profile.viewModels.UserProfileViewModel
import com.miso.dermoapp.ui.core.profile.viewModels.UserProfileViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSnackBar
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi


@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class UserProfile : AppCompatActivity() {
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var loadingDialog: LoadingDialog

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResponseErrorField.initialize(applicationContext)
        val viewModelFactory = UserProfileViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserProfileViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoTuPerfilDeUsuario))

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.citiesList.observe(this, {
                citiesList -> binding.textViewCity.setAdapter(ArrayAdapter(this@UserProfile, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,citiesList[0].data))
        })

        viewModel.errorName.observe(this, {
            binding.textViewNameError.text = it
        })

        viewModel.errorAge.observe(this, {
            binding.textViewAgeError.text = it
        })

        viewModel.errorCity.observe(this, {
            binding.textViewCityError.text = it
        })

        viewModel.buttonContinueDrawable.observe(this, {
            binding.buttonContinue.setBackgroundResource(it)
        })

        viewModel.buttonContinueEnable.observe(this, {
            binding.buttonContinue.isEnabled = it
        })

        viewModel.editTextNameDrawable.observe(this, {
            binding.editTextName.setBackgroundResource(it)
        })

        viewModel.editTextAgeDrawable.observe(this, {
            binding.editTextAge.setBackgroundResource(it)
        })

        viewModel.autocompleteCityDrawable.observe(this, {
            binding.textViewCity.setBackgroundResource(it)
        })

        viewModel.navigateToDermatologicalProfile.observe(this, {
            if (it) {
                loadingDialog.startLoadingDialog()
                if (viewModel.checkOnline(this))
                    viewModel.saveDataProfile(this)
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

        viewModel.resultUserProfile.observe(this, {
            when(it){
                CodeResponseLoginUser.PERFIL_DERMATOLOGICO.code-> loadingDialog.succesful(R.string.configuracionExitosa)
                CodeResponseLoginUser.ERROR.code -> loadingDialog.error()
            }
            viewModel.delayScreen(it)
        })

        viewModel.validateChangeScreen.observe(this, {
            when(it) {
                CodeResponseLoginUser.ERROR.code -> loadingDialog.hideLoadingDialog()
                CodeResponseLoginUser.PERFIL_DERMATOLOGICO.code -> goToScreen(
                    Intent(
                        this@UserProfile,
                        UserDematologicalProfile::class.java
                    )
                )
            }
        })

    }

    private fun goToScreen(intent: Intent) {
        loadingDialog.hideLoadingDialog()
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        loadingDialog.cerrarSesion(getResources().getString(R.string.importante), getResources().getString(R.string.cerrarSesion),CodeResponseLoginUser.ERROR.code)
    }
}