package com.miso.dermoapp.ui.core.injury.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityFormOfTheInjuryBinding
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.ui.core.injury.viewModels.FormOfTheInjuryViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.FormOfTheInjuryViewModelFactory
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class FormOfTheInjury : AppCompatActivity() {
    private lateinit var viewModel: FormOfTheInjuryViewModel
    private lateinit var binding: ActivityFormOfTheInjuryBinding
    private lateinit var loadingDialog: LoadingDialog
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = FormOfTheInjuryViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[FormOfTheInjuryViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_of_the_injury)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoTuPerfilDeUsuario))

        binding.checkbox1.setOnClickListener {
            if (it is CheckBox)
                viewModel.setFormOfTheInjury(this, getString(R.string.anillo), it.isChecked)
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }

        binding.checkbox2.setOnClickListener {
            if (it is CheckBox)
                viewModel.setFormOfTheInjury(this, getString(R.string.domo), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }

        binding.checkbox3.setOnClickListener {
            if (it is CheckBox)
                viewModel.setFormOfTheInjury(this, getString(R.string.ovalada), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }

        binding.checkbox4.setOnClickListener {
            if (it is CheckBox)
                viewModel.setFormOfTheInjury(this, getString(R.string.redonda), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox5.isChecked = false
            binding.checkbox6.isChecked = false
        }

        binding.checkbox5.setOnClickListener {
            if (it is CheckBox)
                viewModel.setFormOfTheInjury(this, getString(R.string.indefinida), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox6.isChecked = false
        }

        binding.checkbox6.setOnClickListener {
            if (it is CheckBox)
                viewModel.setFormOfTheInjury(this, getString(R.string.enrollada), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
            binding.checkbox5.isChecked = false
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

        viewModel.navigateToNumberOfInjuries.observe(this){
            if (it)
                goToChangeScreen(Intent(this@FormOfTheInjury, NumberOfInjuries::class.java))
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