package com.miso.dermoapp.ui.core.injury.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityNumberOfInjuriesBinding
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.ui.core.injury.viewModels.NumberOfInjuriesViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.NumberOfInjuriesViewModelFactory
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class NumberOfInjuries : AppCompatActivity() {
    private lateinit var viewModel: NumberOfInjuriesViewModel
    private lateinit var binding: ActivityNumberOfInjuriesBinding
    private lateinit var loadingDialog: LoadingDialog
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = NumberOfInjuriesViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[NumberOfInjuriesViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number_of_injuries)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoTuPerfilDeUsuario))

        binding.checkbox1.setOnClickListener {
            if (it is CheckBox)
                viewModel.setNumberOfInjuries(this, getString(R.string.solitaria), it.isChecked)
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
        }

        binding.checkbox2.setOnClickListener {
            if (it is CheckBox)
                viewModel.setNumberOfInjuries(this, getString(R.string.m_ltiple), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
        }

        binding.checkbox3.setOnClickListener {
            if (it is CheckBox)
                viewModel.setNumberOfInjuries(this, getString(R.string.recurrente), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox4.isChecked = false
        }

        binding.checkbox4.setOnClickListener {
            if (it is CheckBox)
                viewModel.setNumberOfInjuries(this, getString(R.string.diseminada), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
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

        viewModel.navigateToDistribution.observe(this){
            if (it)
                goToChangeScreen(Intent(this@NumberOfInjuries, Distribution::class.java))
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