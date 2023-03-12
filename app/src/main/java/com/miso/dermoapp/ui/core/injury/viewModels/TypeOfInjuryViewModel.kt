package com.miso.dermoapp.ui.core.injury.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.domain.useCases.TypeOfInjuryUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 2:38 p. m.
 * All rights reserved 2023.
 ****/

class TypeOfInjuryViewModel: ViewModel() {
    private var typeOfInjuryData= MutableLiveData<String>()
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    private val typeOfInjuryUseCase = TypeOfInjuryUseCase()
    fun setTypeOfInjury(context: Context, typeOfInjury: String, checked: Boolean) {
        if (checked)
            typeOfInjuryData.value = typeOfInjury
        else
            typeOfInjuryData.value = ""
        typeOfInjuryUseCase.saveTypeOfInjury(context,typeOfInjury)
        changeEnableButton()
    }

    fun changeEnableButton() {
        if (typeOfInjuryData.value?.isNotEmpty() == true) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }


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