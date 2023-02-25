package com.miso.dermoapp.ui.core.profile.viewModels

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.city.repository.CityRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.models.entities.UserProfileData
import com.miso.dermoapp.domain.models.enumerations.CodeField
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.domain.models.utils.UtilsFields
import com.miso.dermoapp.domain.useCases.UserProfileUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

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

    init {
        errorName.value = ResponseErrorField.DEFAULT.label
        errorAge.value = ResponseErrorField.DEFAULT.label
        errorCity.value = ResponseErrorField.DEFAULT.label
        validName.value = 0
        validAge.value = 0
        validCity.value = 0
        userProfle.value = UserProfileData("","","")
    }

    fun areFieldsEmpty(text: Editable?, field: Int) {
        if (UtilsFields().areFieldsEmpty(text.toString())) {
            setErrorText(field, ResponseErrorField.ERROR_EMPTY.label)
            when (field) {
                CodeField.NAME_FIELD.code -> validName.value = 0
                CodeField.AGE_FIELD.code -> validAge.value = 0
                CodeField.CITY_FIELD.code -> validCity.value = 0
            }
            changeEnableButton()
        } else {
            setErrorText(field, ResponseErrorField.DEFAULT.label)
            when (field) {
                CodeField.NAME_FIELD.code -> {
                    userProfle.value!!.name = text.toString()
                    isValidName(text,3)
                }
                /*CodeField.PASSWORD_FIELD.code -> {
                    userAccount.value!!.password = text.toString()
                    isValidLong(
                        text,
                        CodeField.PASSWORD_FIELD.code,
                        CodeLong.PASSWORD_FIELD.code,
                        validPassword
                    )
                    arePasswordsEqual(userAccount.value!!.passwordConfirm,userAccount.value!!.password)
                }
                CodeField.PASSWORD_CONFIRM_FIELD.code -> {
                    userAccount.value!!.passwordConfirm = text.toString()
                    arePasswordsEqual(userAccount.value!!.passwordConfirm,userAccount.value!!.password)
                }*/
            }
        }
    }

    private fun isValidName(text: Editable?,minValue: Int) {
        if (UtilsFields().isValidLong(text.toString(),minValue)) {
            setErrorText(CodeField.NAME_FIELD.code, ResponseErrorField.DEFAULT.label)
            editTextNameDrawable.value = R.drawable.input_successful
            validName.value = 1
            changeEnableButton()
        } else {
            setErrorText(CodeField.NAME_FIELD.code, ResponseErrorField.ERROR_LONG_CHARACTERS.label + minValue + ResponseErrorField.ERROR_CHARACTERS.label)
            editTextNameDrawable.value = R.drawable.input_error
            validName.value = 0
            changeEnableButton()
        }
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

    private fun setErrorText(field: Int, value: String) {
        when (field) {
            CodeField.NAME_FIELD.code -> errorName.value = value
            CodeField.AGE_FIELD.code -> errorAge.value = value
            CodeField.CITY_FIELD.code -> errorCity.value = value
        }
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