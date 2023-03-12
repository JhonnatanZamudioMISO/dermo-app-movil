package com.miso.dermoapp.ui.core.injury.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityInjuriesBinding
import com.miso.dermoapp.ui.core.injury.viewModels.InjuriesViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.InjuriesViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomRecyclerViewAdapter
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("DEPRECATION")
class Injuries : AppCompatActivity() {
    private lateinit var viewModel: InjuriesViewModel
    private lateinit var binding: ActivityInjuriesBinding
    val adapter = CustomRecyclerViewAdapter()
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = InjuriesViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[InjuriesViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_injuries)
        binding.lifecycleOwner = this
        binding.vModel = viewModel

        binding.recyclerview.adapter = adapter
        binding.textViewMessage.text = getString(R.string.cargando)

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.injuriesList.observe(this){
            if (it.isEmpty()) {
                binding.textViewMessage.visibility = View.VISIBLE
                binding.textViewMessage.text = getString(R.string.actualmente_no_se_ha_registrado_ninguna_lesi_n_dermatol_gica)
            }
            else {
                binding.textViewMessage.visibility = View.INVISIBLE
                adapter.setInjuryList(it)
            }
        }
    }
}