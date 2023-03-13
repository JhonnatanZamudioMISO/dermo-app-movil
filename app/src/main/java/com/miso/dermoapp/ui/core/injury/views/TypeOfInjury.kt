package com.miso.dermoapp.ui.core.injury.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityTypeOfInjuryBinding
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.ui.core.injury.viewModels.TypeOfInjuryViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.TypeOfInjuryViewModelFactory
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class TypeOfInjury : AppCompatActivity() {
    private lateinit var viewModel: TypeOfInjuryViewModel
    private lateinit var binding: ActivityTypeOfInjuryBinding
    private lateinit var loadingDialog: LoadingDialog
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = TypeOfInjuryViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[TypeOfInjuryViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_type_of_injury)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoTuPerfilDeUsuario))

        binding.checkbox1.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.m_cula), it.isChecked)
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
            binding.checkbox7.isChecked = false
            binding.checkbox8.isChecked = false
        }

        binding.checkbox2.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.p_pula), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
            binding.checkbox7.isChecked = false
            binding.checkbox8.isChecked = false
        }

        binding.checkbox3.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.parche), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
            binding.checkbox7.isChecked = false
            binding.checkbox8.isChecked = false
        }

        binding.checkbox4.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.placa), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
            binding.checkbox7.isChecked = false
            binding.checkbox8.isChecked = false
        }

        binding.checkbox5.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.n_dulo), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox6.isChecked = false
            binding.checkbox7.isChecked = false
            binding.checkbox8.isChecked = false
        }

        binding.checkbox6.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.ampolla), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox7.isChecked = false
            binding.checkbox8.isChecked = false
        }

        binding.checkbox7.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.lcera), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
            binding.checkbox8.isChecked = false
        }

        binding.checkbox8.setOnClickListener {
            if (it is CheckBox)
                viewModel.setTypeOfInjury(this, getString(R.string.ves_cula), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
            binding.checkbox7.isChecked = false
        }

        viewModel.buttonContinueDrawable.observe(this, {
            binding.buttonContinue.setBackgroundResource(it)
        })

        viewModel.buttonContinueEnable.observe(this, {
            binding.buttonContinue.isEnabled = it
        })

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.navigateToFormOfTheInjury.observe(this){
            if (it)
                goToChangeScreen(Intent(this@TypeOfInjury, FormOfTheInjury::class.java))
        }
    }

    private fun goToChangeScreen(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        loadingDialog.closeTypeOfInjury(getResources().getString(R.string.importante), getResources().getString(R.string.messageCloseType),
            CodeResponseLoginUser.ERROR.code)
    }
}