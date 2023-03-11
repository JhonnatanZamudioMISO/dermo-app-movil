package com.miso.dermoapp.ui.core.dashboard.viewModels

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