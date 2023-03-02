package com.miso.dermoapp.ui.core.profile.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityUserDematologicalProfileBinding
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModel
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModelFactory
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

class UserDematologicalProfile : AppCompatActivity() {
    private lateinit var viewModel: UserDermatologicalProfileViewModel
    private lateinit var binding: ActivityUserDematologicalProfileBinding
    private lateinit var loadingDialog: LoadingDialog
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResponseErrorField.initialize(applicationContext)
        val viewModelFactory = UserDermatologicalProfileViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserDermatologicalProfileViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_dematological_profile)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoPerfilDermatologico))
    }
}