package com.miso.dermoapp.ui.core.injury.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.domain.useCases.NumberOfInjuriesUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 6:52 p. m.
 * All rights reserved 2023.
 ****/

class NumberOfInjuriesViewModel: ViewModel() {
    private var numberOfInjuriesData= MutableLiveData<String>()
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    private val numberOfInjuriesUseCase = NumberOfInjuriesUseCase()
    val navigateToDistribution = MutableLiveData<Boolean>()

    init {
        navigateToDistribution.value = false
    }
    fun setNumberOfInjuries(context: Context, numberOfInjuries: String, checked: Boolean) {
        if (checked)
            numberOfInjuriesData.value = numberOfInjuries
        else
            numberOfInjuriesData.value = ""
        numberOfInjuriesUseCase.saveNumberOfInjuries(context,numberOfInjuries)
        changeEnableButton()
    }

    fun changeEnableButton() {
        if (numberOfInjuriesData.value?.isNotEmpty() == true) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    fun Continue() {
        navigateToDistribution.value = true
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class NumberOfInjuriesViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: NumberOfInjuriesViewModelFactory? = null
        fun getInstance(): NumberOfInjuriesViewModelFactory = instance ?: synchronized(this) {
            instance ?: NumberOfInjuriesViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NumberOfInjuriesViewModel() as T
    }
}