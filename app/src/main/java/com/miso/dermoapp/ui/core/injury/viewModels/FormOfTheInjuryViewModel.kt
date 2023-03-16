package com.miso.dermoapp.ui.core.injury.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.domain.useCases.FormOfTheInjuryUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 6:20 p. m.
 * All rights reserved 2023.
 ****/

class FormOfTheInjuryViewModel: ViewModel() {
    private var formOfTheInjuryData= MutableLiveData<String>()
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    private val formOfTheInjuryUseCase = FormOfTheInjuryUseCase()
    val navigateToNumberOfInjuries = MutableLiveData<Boolean>()

    init {
        navigateToNumberOfInjuries.value = false
    }
    fun setFormOfTheInjury(context: Context, formOfTheInjury: String, checked: Boolean) {
        if (checked)
            formOfTheInjuryData.value = formOfTheInjury
        else
            formOfTheInjuryData.value = ""
        formOfTheInjuryUseCase.saveFormOfTheInjury(context,formOfTheInjury)
        changeEnableButton()
    }

    fun changeEnableButton() {
        if (formOfTheInjuryData.value?.isNotEmpty() == true) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    fun Continue() {
        navigateToNumberOfInjuries.value = true
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class FormOfTheInjuryViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: FormOfTheInjuryViewModelFactory? = null
        fun getInstance(): FormOfTheInjuryViewModelFactory = instance ?: synchronized(this) {
            instance ?: FormOfTheInjuryViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormOfTheInjuryViewModel() as T
    }
}