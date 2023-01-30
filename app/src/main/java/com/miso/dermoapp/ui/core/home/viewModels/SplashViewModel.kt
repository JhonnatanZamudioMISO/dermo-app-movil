package com.miso.dermoapp.ui.core.home.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miso.dermoapp.domain.useCases.SplashUseCase

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.presentation.core.home.viewModels
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 12:43 p. m.
 * All rights reserved 2023.
 ****/

class SplashViewModel: ViewModel() {
    val version = MutableLiveData<String>()
    val splashUseCase  = SplashUseCase()

    init {
        getAppVersion()
    }

    fun setVersion(v:String){
        version.value = v
    }

    fun getAppVersion(){
        setVersion(splashUseCase.getAppVersion())
    }
}