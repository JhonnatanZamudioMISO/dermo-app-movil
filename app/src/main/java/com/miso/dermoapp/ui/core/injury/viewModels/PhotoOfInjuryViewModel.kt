package com.miso.dermoapp.ui.core.injury.viewModels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.injury.entitie.RequestInjury
import com.miso.dermoapp.data.attributes.injury.repository.InjuryRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.models.entities.UserInjury
import com.miso.dermoapp.domain.models.enumerations.CodeSnackBarCloseAction
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.UtilsNetwork
import com.miso.dermoapp.domain.useCases.PhotoOfInjuryUseCase
import kotlinx.coroutines.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.injury.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 8:11 p. m.
 * All rights reserved 2023.
 ****/

class PhotoOfInjuryViewModel(injuryRepository: InjuryRepository): ViewModel() {
    val statusPhoto = MutableLiveData<Boolean>()
    val snackBarAction = MutableLiveData<Int>()
    val snackBarNavigate = MutableLiveData<Int>()
    val snackBarTextWarning = MutableLiveData<String>()
    private var validFoto = MutableLiveData<Int>()
    var userInjury = MutableLiveData<UserInjury>()
    private val photoOfInjuryUseCase = PhotoOfInjuryUseCase(injuryRepository)
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    val navigateToInjury = MutableLiveData<Boolean>()
    val resultCreateInjury = MutableLiveData<Int>()
    val validateChangeScreen = MutableLiveData<Int>()

    init {
        statusPhoto.value = false
        snackBarNavigate.value = CodeSnackBarCloseAction.NONE.code
        validFoto.value = 0
        userInjury.value = UserInjury("","","","","","")
        navigateToInjury.value = false
        validateChangeScreen.value = -1
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delayScreen(code: Int){
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                delay(1500)
            }
            validateChangeScreen.postValue(code)
        }
    }

    fun AddPhoto() {
        statusPhoto.value = true
    }

    fun validatePhoto(bitmap: Bitmap) {
        validFoto.value = 1
        userInjury.value!!.photoOfInjury = photoOfInjuryUseCase.getBase64Photo(bitmap)
        changeEnableButton()
    }

    private fun changeEnableButton() {
        if (validFoto.value == 1) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    fun ContinueAddInjury() {
        navigateToInjury.value = true
    }

    fun checkOnline(context: Context): Boolean {
        getDataInjury(context)
        return UtilsNetwork().isOnline(context)
    }

    fun getDataInjury(context: Context){
        userInjury.value!!.email = photoOfInjuryUseCase.getDataPreferences(context, KeySharedPreferences.EMAIL.value)
        userInjury.value!!.typeOfInjury = photoOfInjuryUseCase.getDataPreferences(context, KeySharedPreferences.TYPE_OF_INJURY.value)
        userInjury.value!!.formOfInjury = photoOfInjuryUseCase.getDataPreferences(context, KeySharedPreferences.FORM_OF_THE_INJURY.value)
        userInjury.value!!.numberOfTheInjuries = photoOfInjuryUseCase.getDataPreferences(context, KeySharedPreferences.NUMBER_OF_INJURIES.value)
        userInjury.value!!.distribution = photoOfInjuryUseCase.getDataPreferences(context, KeySharedPreferences.DISTRIBUTION.value)
    }

    fun createInjury() {
        viewModelScope.launch {
            val userInjuryData = RequestInjury(
                userInjury.value!!.email,
                userInjury.value!!.typeOfInjury,
                userInjury.value!!.formOfInjury,
                userInjury.value!!.numberOfTheInjuries,
                userInjury.value!!.distribution,
                userInjury.value!!.photoOfInjury
            )
            resultCreateInjury.value = photoOfInjuryUseCase.createInjury(userInjuryData)
        }
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class PhotoOfInjuryViewModelFactory(
    private val injuryRepository: InjuryRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: PhotoOfInjuryViewModelFactory? = null
        fun getInstance(): PhotoOfInjuryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PhotoOfInjuryViewModelFactory(
                    Injection.providerInjuryRepository()
                )
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhotoOfInjuryViewModel(injuryRepository) as T
    }
}