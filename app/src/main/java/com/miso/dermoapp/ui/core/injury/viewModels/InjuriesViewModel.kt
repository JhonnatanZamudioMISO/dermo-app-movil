package com.miso.dermoapp.ui.core.injury.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.data.attributes.injury.repository.InjuryRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 11/03/2023 at 9:35 p. m.
 * All rights reserved 2023.
 ****/

class InjuriesViewModel(injuryRepository: InjuryRepository): ViewModel() {

}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class InjuriesViewModelFactory(
    private val injuryRepository: InjuryRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: InjuriesViewModelFactory? = null
        fun getInstance(): InjuriesViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: InjuriesViewModelFactory(
                    Injection.providerInjuryRepository()
                )
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InjuriesViewModel(injuryRepository) as T
    }
}