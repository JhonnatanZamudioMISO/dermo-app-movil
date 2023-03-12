package com.miso.dermoapp.ui.core.injury.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 2:38 p. m.
 * All rights reserved 2023.
 ****/

class TypeOfInjuryViewModel: ViewModel() {

}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class TypeOfInjuryViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: TypeOfInjuryViewModelFactory? = null
        fun getInstance(): TypeOfInjuryViewModelFactory = instance ?: synchronized(this) {
            instance ?: TypeOfInjuryViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TypeOfInjuryViewModel() as T
    }
}