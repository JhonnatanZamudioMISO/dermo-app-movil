package com.miso.dermoapp.ui.core.injury.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityTypeOfInjuryBinding
import com.miso.dermoapp.ui.core.injury.viewModels.TypeOfInjuryViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.TypeOfInjuryViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class TypeOfInjury : AppCompatActivity() {
    private lateinit var viewModel: TypeOfInjuryViewModel
    private lateinit var binding: ActivityTypeOfInjuryBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = TypeOfInjuryViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[TypeOfInjuryViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_type_of_injury)
        binding.lifecycleOwner = this
        binding.vModel = viewModel

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
    }
}