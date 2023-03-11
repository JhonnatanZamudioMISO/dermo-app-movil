package com.miso.dermoapp.ui.core.dashboard.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.dashboard.viewModels
 * Created by Jhonnatan E. Zamudio P. on 6/03/2023 at 5:32 a. m.
 * All rights reserved 2023.
 ****/

class DashboardViewModel: ViewModel() {
    val navigateToInjuries = MutableLiveData<Boolean>()
    val navigateToDiagnosis = MutableLiveData<Boolean>()

    init {
        navigateToInjuries.value = false
        navigateToDiagnosis.value = false
    }

    fun NavigateToInjuries() {
        navigateToInjuries.value = true
    }

    fun NavigateToDiagnosis() {
        navigateToDiagnosis.value = true
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class DashboardViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: DashboardViewModelFactory? = null
        fun getInstance(): DashboardViewModelFactory = instance ?: synchronized(this) {
            instance ?: DashboardViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel() as T
    }
}