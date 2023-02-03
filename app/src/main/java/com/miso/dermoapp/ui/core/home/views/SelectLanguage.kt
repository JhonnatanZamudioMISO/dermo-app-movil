package com.miso.dermoapp.ui.core.home.views


import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySelectLanguageBinding
import com.miso.dermoapp.domain.models.enumerations.CodeSharedPreferences
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.ui.core.home.viewModels.SelectLanguageViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SelectLanguageViewModelFactory
import kotlinx.coroutines.*
import java.util.*


@Suppress("DEPRECATION")
class SelectLanguage : AppCompatActivity() {
    private lateinit var viewModel: SelectLanguageViewModel
    private lateinit var binding: ActivitySelectLanguageBinding
    private lateinit var language: String
    private val config: Configuration = Configuration()
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
        binding.textViewIngles.setOnClickListener {
            visualizeEnglish()
            config.locale = Locale("en")
            resources.updateConfiguration(config, null)
            goToChangeScreen(0,KeySharedPreferences.ENGLISH.value)
        }
        binding.textViewEspanol.setOnClickListener {
            visualizeSpanish()
            config.locale = Locale("es")
            resources.updateConfiguration(config, null)
            goToChangeScreen(0,KeySharedPreferences.SPANISH.value)
        }
        binding.btnSiguiente.setOnClickListener {
            viewModel.saveDefaultLanguage(this)
            goToChangeScreen(1,null)
        }
    }

    private fun goToChangeScreen(screen: Int, value: String?) {
        var intent: Intent
        lifecycleScope.launch{
            withContext(Dispatchers.IO){
                when(screen){
                    0 -> {
                        intent = Intent(this@SelectLanguage, SelectLanguage::class.java)
                        intent.putExtra(KeySharedPreferences.IDIOMA.value, value)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    }
                    1 -> {
                        intent = Intent(this@SelectLanguage, Welcome::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.left_in,R.anim.left_out)
                    }
                }
                finish()
            }
        }
    }
    private fun defaultLanguage() {
        if (language.equals(KeySharedPreferences.SPANISH.value)) {
            visualizeSpanish()
            viewModel.setLanguage(CodeSharedPreferences.SPANISH.code)
        } else {
            visualizeEnglish()
            viewModel.setLanguage(CodeSharedPreferences.ENGLISH.code)
        }
    }

    private fun visualizeEnglish() {
        binding.Linea1.setAlpha(1F)
        binding.Linea3.setAlpha(0F)
        binding.textViewIngles.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_estados_unidos, 0, 0, 0)
        binding.textViewEspanol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_colombia1, 0, 0, 0)
        binding.textViewIngles.setTextColor(Color.parseColor("#FFFFFF"))
        binding.textViewEspanol.setTextColor(Color.parseColor("#99FFFFFF"))
        binding.textViewIngles.setEnabled(false)
    }

    private fun visualizeSpanish() {
        binding.Linea1.setAlpha(0F)
        binding.Linea3.setAlpha(1F)
        binding.textViewIngles.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_estados_unidos1, 0, 0, 0)
        binding.textViewEspanol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.escudo_colombia, 0, 0, 0)
        binding.textViewIngles.setTextColor(Color.parseColor("#99FFFFFF"))
        binding.textViewEspanol.setTextColor(Color.parseColor("#FFFFFF"))
        binding.textViewEspanol.setEnabled(false)
    }

}