package com.miso.dermoapp.ui.core.injury.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityInjuriesBinding
import com.miso.dermoapp.ui.core.injury.viewModels.InjuriesViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.InjuriesViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

class Injuries : AppCompatActivity() {
    private lateinit var viewModel: InjuriesViewModel
    private lateinit var binding: ActivityInjuriesBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = InjuriesViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[InjuriesViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_injuries)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
    }
}