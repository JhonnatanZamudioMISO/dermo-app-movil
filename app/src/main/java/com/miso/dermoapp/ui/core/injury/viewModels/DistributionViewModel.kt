package com.miso.dermoapp.ui.core.injury.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.domain.useCases.DistributionUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 7:39 p. m.
 * All rights reserved 2023.
 ****/

class DistributionViewModel: ViewModel() {
    private var distributionData= MutableLiveData<String>()
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    private val distributionUseCase = DistributionUseCase()
    val navigateToPhotoOfInjury = MutableLiveData<Boolean>()

    init {
        navigateToPhotoOfInjury.value = false
    }
    fun setDistribution(context: Context, distribution: String, checked: Boolean) {
        if (checked)
            distributionData.value = distribution
        else
            distributionData.value = ""
        distributionUseCase.saveDistribution(context,distribution)
        changeEnableButton()
    }

    fun changeEnableButton() {
        if (distributionData.value?.isNotEmpty() == true) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    fun Continue() {
        navigateToPhotoOfInjury.value = true
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class DistributionViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: DistributionViewModelFactory? = null
        fun getInstance(): DistributionViewModelFactory = instance ?: synchronized(this) {
            instance ?: DistributionViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DistributionViewModel() as T
    }
}