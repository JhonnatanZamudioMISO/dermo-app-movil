package com.miso.dermoapp.ui.core.home.viewModels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.domain.useCases.SelectLanguageUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.home.viewModels
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 7:33 p. m.
 * All rights reserved 2023.
 ****/

class SelectLanguageViewModel (): ViewModel() {
    val language = MutableLiveData<String>()
    private val selectLanguageUseCase = SelectLanguageUseCase()
    fun setLanguage(v:String){
        language.value = v
    }

    fun saveDefaultLanguage(context: Context){
        selectLanguageUseCase.saveDefaultLanguage(context, language.value!!)
    }
}


@SuppressLint("SelectLanguage")
@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class SelectLanguageViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: SelectLanguageViewModelFactory? = null
        fun getInstance(): SelectLanguageViewModelFactory = instance ?: synchronized(this) {
            instance ?: SelectLanguageViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectLanguageViewModel() as T
    }
}