package com.miso.dermoapp.ui.core.profile.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityUserDematologicalProfileBinding
import com.miso.dermoapp.domain.models.enumerations.CodeTypeSpinner
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModel
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSpinnerAdapter
import com.miso.dermoapp.ui.core.utils.ListDialog
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

class UserDematologicalProfile : AppCompatActivity() {
    private lateinit var viewModel: UserDermatologicalProfileViewModel
    private lateinit var binding: ActivityUserDematologicalProfileBinding
    private lateinit var loadingDialog: LoadingDialog
    private val tag = "UserDermaologicalProfile"
    @SuppressLint("SetTextI18n")
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

        binding.buttonTypeOfKin.setSelected(true)
        binding.buttonTypeOfKin.text = getString(R.string.espacio) +getString(R.string.seleccion_tipo_piel)

        binding.buttonTypeOfKin.setOnClickListener {
            createDialogSpinner(viewModel.typeKinsList, CodeTypeSpinner.TYPE_KIN.code)
        }

        viewModel.typeKinSelectedPosition.observe(this) {
            if (it != null)
                binding.buttonTypeOfKin.text =
                    getString(R.string.espacio) + viewModel.typeKinsList[it].abbreviate + getString(R.string.separador) + viewModel.typeKinsList[it].description
        }
    }

    private fun createDialogSpinner(dataList: List<Any>, code: Int) {
        val dialog = ListDialog(
            dataList,
            code,
            object : CustomSpinnerAdapter.CustomActionSpinner {
                override fun onItemSelected(position: Int) {
                    when (code) {
                        CodeTypeSpinner.TYPE_KIN.code -> viewModel.typeKinSelectedPosition.value =
                            position
                    }
                }
            })
        dialog.show(this.supportFragmentManager, tag)
    }
}