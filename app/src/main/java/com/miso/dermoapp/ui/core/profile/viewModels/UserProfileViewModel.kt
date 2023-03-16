package com.miso.dermoapp.ui.core.profile.viewModels

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.city.entitie.ResponseCities
import com.miso.dermoapp.data.attributes.city.repository.CityRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.models.entities.UserProfileData
import com.miso.dermoapp.domain.models.enumerations.CodeField
import com.miso.dermoapp.domain.models.enumerations.CodeSnackBarCloseAction
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.domain.models.utils.UtilsFields
import com.miso.dermoapp.domain.models.utils.UtilsNetwork
import com.miso.dermoapp.domain.useCases.UserProfileUseCase
import kotlinx.coroutines.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.profile.viewModels
 * Created by Jhonnatan E. Zamudio P. on 23/02/2023 at 9:05 p. m.
 * All rights reserved 2023.
 ****/

class UserProfileViewModel(cityRepository: CityRepository): ViewModel() {
    val errorName = MutableLiveData<String>()
    val errorAge = MutableLiveData<String>()
    val errorCity = MutableLiveData<String>()
    private var validName = MutableLiveData<Int>()
    private var validAge = MutableLiveData<Int>()
    private var validCity = MutableLiveData<Int>()
    private val userProfileUseCase = UserProfileUseCase(cityRepository)
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    var userProfle = MutableLiveData<UserProfileData>()
    val editTextNameDrawable = MutableLiveData<Int>()
    val editTextAgeDrawable = MutableLiveData<Int>()
    val autocompleteCityDrawable = MutableLiveData<Int>()
    val citiesList: MutableLiveData<ArrayList<ResponseCities>> = MutableLiveData<ArrayList<ResponseCities>>()
    val navigateToDermatologicalProfile = MutableLiveData<Boolean>()
    val snackBarAction = MutableLiveData<Int>()
    val snackBarNavigate = MutableLiveData<Int>()
    val snackBarTextWarning = MutableLiveData<String>()
    val validateChangeScreen = MutableLiveData<Int>()
    val resultUserProfile = MutableLiveData<Int>()


    init {
        errorName.value = ResponseErrorField.DEFAULT.label
        errorAge.value = ResponseErrorField.DEFAULT.label
        errorCity.value = ResponseErrorField.DEFAULT.label
        validName.value = 0
        validAge.value = 0
        validCity.value = 0
        userProfle.value = UserProfileData("","","")
        getDataCitiesByCodeCountry()
        navigateToDermatologicalProfile.value = false
        snackBarNavigate.value = CodeSnackBarCloseAction.NONE.code
        validateChangeScreen.value = -1
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun saveDataProfile(context: Context){
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                delay(500)
            }
            resultUserProfile.postValue(userProfileUseCase.setDataProfile(context,userProfle.value))
        }
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

    fun checkOnline(context: Context): Boolean {
        return UtilsNetwork().isOnline(context)
    }

    fun continueProcess() {
        navigateToDermatologicalProfile.value = true
    }

    fun getDataCitiesByCodeCountry() {
        viewModelScope.launch {
            citiesList.value =
                ArrayList(userProfileUseCase.getDataCitiesByCodeCountry("Colombia"))
        }
    }

    fun areFieldsEmpty(text: Editable?, field: Int) {
        if (UtilsFields().areFieldsEmpty(text.toString())) {
            setErrorText(field, ResponseErrorField.ERROR_EMPTY.label, R.drawable.input_error, 0)
            when (field) {
                CodeField.NAME_FIELD.code -> validName.value = 0
                CodeField.AGE_FIELD.code -> validAge.value = 0
                CodeField.CITY_FIELD.code -> validCity.value = 0
            }
        } else {
            setErrorText(field, ResponseErrorField.DEFAULT.label, R.drawable.input_successful, 1)
            when (field) {
                CodeField.NAME_FIELD.code -> {
                    userProfle.value!!.name = text.toString()
                    isValidName(text,3)
                }
                CodeField.AGE_FIELD.code -> {
                    userProfle.value!!.age = text.toString()
                    isValidAge(text)
                }
                CodeField.CITY_FIELD.code -> {
                    userProfle.value!!.city = text.toString()
                    isValidCity(text.toString())
                }
            }
        }
    }

    private fun isValidCity(city: String) {
        if (userProfileUseCase.isCityInList(city, citiesList.value!!))
            setErrorText(CodeField.CITY_FIELD.code, ResponseErrorField.DEFAULT.label,R.drawable.input_successful,1)
        else
            setErrorText(CodeField.CITY_FIELD.code, ResponseErrorField.ERROR_INVALID_CITY.label,R.drawable.input_error,0)
    }

    private fun isValidAge(text: Editable?) {
        if (UtilsFields().isMajorZero(text.toString()))
            setErrorText(CodeField.AGE_FIELD.code,ResponseErrorField.DEFAULT.label,R.drawable.input_successful,1)
        else
            setErrorText(CodeField.AGE_FIELD.code,ResponseErrorField.ERROR_AGE_MAJOR_ZERO.label,R.drawable.input_error,0)
    }

    private fun isValidName(text: Editable?,minValue: Int) {
        if (UtilsFields().isValidLong(text.toString(),minValue))
            setErrorText(CodeField.NAME_FIELD.code, ResponseErrorField.DEFAULT.label,R.drawable.input_successful,1)
        else
            setErrorText(CodeField.NAME_FIELD.code, ResponseErrorField.ERROR_LONG_CHARACTERS.label + minValue + ResponseErrorField.ERROR_CHARACTERS.label, R.drawable.input_error, 0)
    }

    private fun changeEnableButton() {
        if (userProfileUseCase.changeEnableButton(validName.value!!, validAge.value!!, validCity.value!!)) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    private fun setErrorText(field: Int, value: String, status: Int, state: Int) {
        when (field) {
            CodeField.NAME_FIELD.code -> {
                errorName.value = value
                editTextNameDrawable.value = status
                validName.value = state
            }
            CodeField.AGE_FIELD.code -> {
                errorAge.value = value
                editTextAgeDrawable.value = status
                validAge.value = state
            }
            CodeField.CITY_FIELD.code -> {
                errorCity.value = value
                autocompleteCityDrawable.value = status
                validCity.value = state
            }
        }
        changeEnableButton()
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class UserProfileViewModelFactory(
    private val cityRepository: CityRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: UserProfileViewModelFactory? = null
        fun getInstance(context: Context): UserProfileViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: UserProfileViewModelFactory(
                    Injection.providerCityRepository(context)
                )
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(cityRepository) as T
    }
}