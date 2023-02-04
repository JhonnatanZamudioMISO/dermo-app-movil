package com.miso.dermoapp.ui.core.session.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.session.viewModels
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 3:17 p. m.
 * All rights reserved 2023.
 ****/

class SignUpViewModel : ViewModel() {

}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: SignUpViewModelFactory? = null
        fun getInstance(): SignUpViewModelFactory = instance ?: synchronized(this) {
            instance ?: SignUpViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel() as T
    }
}