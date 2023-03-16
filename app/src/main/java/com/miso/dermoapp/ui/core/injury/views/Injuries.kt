package com.miso.dermoapp.ui.core.injury.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.injury.entitie.Injuries
import com.miso.dermoapp.databinding.ActivityInjuriesBinding
import com.miso.dermoapp.ui.core.injury.viewModels.InjuriesViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.InjuriesViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomRecyclerViewAdapter
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("DEPRECATION")
class Injuries : AppCompatActivity(), CustomRecyclerViewAdapter.CellClickListener {
    private lateinit var viewModel: InjuriesViewModel
    private lateinit var binding: ActivityInjuriesBinding
    private lateinit var loadingDialog: LoadingDialog
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = InjuriesViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[InjuriesViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_injuries)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        val adapter = CustomRecyclerViewAdapter(this)
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoTuPerfilDeUsuario))

        binding.recyclerview.adapter = adapter
        binding.textViewMessage.text = getString(R.string.cargando)
        viewModel.getDataInjuries(this)

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

        viewModel.navigateToTypeOfInjury.observe(this){
            if(it)
                goToChangeScreen(Intent(this@Injuries, TypeOfInjury::class.java))
        }

        viewModel.diagnosisInjury.observe(this) {
            loadingDialog.mostrarDataDiagnostico(getResources().getString(R.string.importante),it)
        }
    }
    private fun goToChangeScreen(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }

    override fun onCellClickListener(injuries: Injuries) {
        viewModel.getDataDiagnosis(injuries.id)
    }

}