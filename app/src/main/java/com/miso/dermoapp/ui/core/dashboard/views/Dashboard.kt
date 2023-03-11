package com.miso.dermoapp.ui.core.dashboard.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityDashboardBinding
import com.miso.dermoapp.ui.core.dashboard.viewModels.DashboardViewModel
import com.miso.dermoapp.ui.core.dashboard.viewModels.DashboardViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

class Dashboard : AppCompatActivity() {
    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: ActivityDashboardBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = DashboardViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
    }
}