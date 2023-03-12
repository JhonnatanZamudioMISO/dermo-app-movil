package com.miso.dermoapp.ui.core.injury.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityTypeOfInjuryBinding
import com.miso.dermoapp.ui.core.injury.viewModels.TypeOfInjuryViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.TypeOfInjuryViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

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
    }
}