package com.miso.dermoapp.ui.core.injury.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityDistributionBinding
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.ui.core.injury.viewModels.DistributionViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.DistributionViewModelFactory
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class Distribution : AppCompatActivity() {
    private lateinit var viewModel: DistributionViewModel
    private lateinit var binding: ActivityDistributionBinding
    private lateinit var loadingDialog: LoadingDialog
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = DistributionViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[DistributionViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_distribution)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoTuPerfilDeUsuario))

        binding.checkbox1.setOnClickListener {
            if (it is CheckBox)
                viewModel.setDistribution(this, getString(R.string.asim_trica), it.isChecked)
            binding.checkbox2.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
        }

        binding.checkbox2.setOnClickListener {
            if (it is CheckBox)
                viewModel.setDistribution(this, getString(R.string.confluente), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox3.isChecked = false
            binding.checkbox4.isChecked = false
        }

        binding.checkbox3.setOnClickListener {
            if (it is CheckBox)
                viewModel.setDistribution(this, getString(R.string.sim_trica), it.isChecked)
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
            binding.checkbox4.isChecked = false
        }

        binding.checkbox4.setOnClickListener {
            if (it is CheckBox)
                viewModel.setDistribution(this, getString(R.string.esparcida), it.isChecked)
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

        viewModel.navigateToPhotoOfInjury.observe(this){
            if (it)
                goToChangeScreen(Intent(this@Distribution, PhotoOfInjury::class.java))
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