package com.miso.dermoapp.ui.core.home.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySelectLanguageBinding
import com.miso.dermoapp.ui.core.home.viewModels.SelectLanguageViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SelectLanguageViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

class SelectLanguage : AppCompatActivity() {
    private lateinit var viewModel: SelectLanguageViewModel
    private lateinit var binding: ActivitySelectLanguageBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = SelectLanguageViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[SelectLanguageViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
    }
}