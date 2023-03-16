package com.miso.dermoapp.ui.core.dashboard.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityDashboardBinding
import com.miso.dermoapp.ui.core.dashboard.viewModels.DashboardViewModel
import com.miso.dermoapp.ui.core.dashboard.viewModels.DashboardViewModelFactory
import com.miso.dermoapp.ui.core.diagnosis.views.Diagnosis
import com.miso.dermoapp.ui.core.injury.views.Injuries
import faker.com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter
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

        binding.buttonDiagnostic.visibility = View.INVISIBLE
        binding.textViewDiagnostic.visibility = View.INVISIBLE

        viewModel.navigateToInjuries.observe(this,{
            if (it)
                goToChangeScreen(Intent(this@Dashboard, Injuries::class.java))
        })
        viewModel.navigateToDiagnosis.observe(this,{
            if (it)
                goToChangeScreen(Intent(this@Dashboard, Diagnosis::class.java))
        })
    }

    private fun goToChangeScreen(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
    }
}