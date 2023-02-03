package com.miso.dermoapp.ui.core.home.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.home.viewModels
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 11:25 a. m.
 * All rights reserved 2023.
 ****/

class WelcomeViewModel: ViewModel() {

}

@SuppressLint("SelectLanguage")
@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class WelcomeViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: WelcomeViewModelFactory? = null
        fun getInstance(): WelcomeViewModelFactory = instance ?: synchronized(this) {
            instance ?: WelcomeViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WelcomeViewModel() as T
    }
}