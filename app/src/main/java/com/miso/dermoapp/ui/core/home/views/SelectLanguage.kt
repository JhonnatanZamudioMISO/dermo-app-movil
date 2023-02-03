package com.miso.dermoapp.ui.core.home.views


import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySelectLanguageBinding
import com.miso.dermoapp.domain.models.enumerations.CodeSharedPreferences
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.ui.core.home.viewModels.SelectLanguageViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SelectLanguageViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi


class SelectLanguage : AppCompatActivity() {
    private lateinit var viewModel: SelectLanguageViewModel
    private lateinit var binding: ActivitySelectLanguageBinding
    private lateinit var language: String
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language = intent.getStringExtra(KeySharedPreferences.IDIOMA.value)!!
        val viewModelFactory = SelectLanguageViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[SelectLanguageViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        defaultLanguage()
    }

    private fun defaultLanguage() {
        if (language.equals("español")) {
            binding.Linea1.setAlpha(0F)
            binding.Linea3.setAlpha(1F)
            binding.textViewIngles.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_estados_unidos1, 0, 0, 0)
            binding.textViewEspanol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_colombia, 0, 0, 0)
            binding.textViewIngles.setTextColor(Color.parseColor("#99FFFFFF"))
            binding.textViewEspanol.setTextColor(Color.parseColor("#FFFFFF"))
            binding.textViewEspanol.setEnabled(false)
            viewModel.setLanguage(CodeSharedPreferences.SPANISH.code)
        } else {
            binding.Linea1.setAlpha(1F)
            binding.Linea3.setAlpha(0F)
            binding.textViewIngles.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_estados_unidos, 0, 0, 0)
            binding.textViewEspanol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_colombia1, 0, 0, 0)
            binding.textViewIngles.setTextColor(Color.parseColor("#FFFFFF"))
            binding.textViewEspanol.setTextColor(Color.parseColor("#99FFFFFF"))
            binding.textViewIngles.setEnabled(false)
            viewModel.setLanguage(CodeSharedPreferences.ENGLISH.code)
        }
    }

}