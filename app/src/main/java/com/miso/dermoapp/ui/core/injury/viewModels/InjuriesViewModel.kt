package com.miso.dermoapp.ui.core.injury.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.dermoapp.data.attributes.injury.entitie.Injuries
import com.miso.dermoapp.data.attributes.injury.repository.InjuryRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.useCases.InjuryUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 11/03/2023 at 9:35 p. m.
 * All rights reserved 2023.
 ****/

class InjuriesViewModel(injuryRepository: InjuryRepository): ViewModel() {
    val injuriesList: MutableLiveData<List<Injuries>> = MutableLiveData<List<Injuries>>()
    val diagnosisInjury: MutableLiveData<DiagnosisData> = MutableLiveData<DiagnosisData>()
    private val injuryUseCase = InjuryUseCase(injuryRepository)
    val navigateToTypeOfInjury = MutableLiveData<Boolean>()
    init {
        navigateToTypeOfInjury.value = false
    }

    fun getDataInjuries(context: Context) {
        viewModelScope.launch {
            injuriesList.value = injuryUseCase.getDataInjuries(context)
        }
    }

    fun NavigateToTypeOfInjury() {
        navigateToTypeOfInjury.value = true
    }

    fun getDataDiagnosis(context: Context, idInjury: String) {
        viewModelScope.launch {
            injuriesList.value = injuryUseCase.getDataInjuries(context)
        }
    }

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