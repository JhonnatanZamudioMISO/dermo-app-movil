package com.miso.dermoapp.ui.core.session.viewModels

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.domain.models.entities.UserAccountData
import com.miso.dermoapp.domain.models.enumerations.CodeField
import com.miso.dermoapp.domain.models.enumerations.CodeLong
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.domain.models.utils.UtilsFields
import com.miso.dermoapp.domain.useCases.SignUpUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.session.viewModels
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 3:17 p. m.
 * All rights reserved 2023.
 ****/

class SignUpViewModel : ViewModel() {
    val errorEmail = MutableLiveData<String>()
    val errorPassword = MutableLiveData<String>()
    val errorPasswordConfirm = MutableLiveData<String>()
    val buttonContinueDrawable = MutableLiveData<Int>()
    val editTextEmailDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    private val signUpUseCase = SignUpUseCase()
    val navigateToLogIn = MutableLiveData<Boolean>()
    var userAccount = MutableLiveData<UserAccountData>()
    var showPassword = MutableLiveData<Boolean>()
    var showPasswordConfirm = MutableLiveData<Boolean>()
    private var passwordCounter = MutableLiveData<Int>()
    private var passwordConfirmCounter = MutableLiveData<Int>()
    private var validEmail = MutableLiveData<Int>()
    private var validPassword = MutableLiveData<Int>()
    private var validPasswordConfirm = MutableLiveData<Int>()


    init {
        navigateToLogIn.value = false
        errorEmail.value = ResponseErrorField.DEFAULT.label
        errorPassword.value = ResponseErrorField.DEFAULT.label
        errorPasswordConfirm.value = ResponseErrorField.DEFAULT.label
        userAccount.value = UserAccountData("","","","", "")
        passwordCounter.value = 0
        passwordConfirmCounter.value = 0
        validEmail.value = 0
        validPassword.value = 0
        validPasswordConfirm.value = 0
    }

    fun areFieldsEmpty(text: Editable?, field: Int) {

        if (UtilsFields().areFieldsEmpty(text.toString())) {
            setErrorText(field, ResponseErrorField.ERROR_EMPTY.label)
            when (field) {
                CodeField.EMAIL_FIELD.code -> validEmail.value = 0
                CodeField.PASSWORD_FIELD.code -> validPassword.value = 0
                CodeField.PASSWORD_CONFIRM_FIELD.code -> validPasswordConfirm.value = 0
            }
            changeEnableButton()
        } else {
            setErrorText(field, ResponseErrorField.DEFAULT.label)
            when (field) {
                CodeField.EMAIL_FIELD.code -> {
                    userAccount.value!!.email = text.toString()
                    userAccount.value!!.id = text.toString()
                    isValidEmail(text)
                }
                CodeField.PASSWORD_FIELD.code -> {
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
                }
            }
        }
    }

    private fun arePasswordsEqual(confirmPassword: String, password: String) {
        if (signUpUseCase.arePasswordsEqual(confirmPassword, password)) {
            setErrorText(CodeField.PASSWORD_CONFIRM_FIELD.code, ResponseErrorField.DEFAULT.label)
            validPasswordConfirm.value = 1
            changeEnableButton()
        } else {
            setErrorText(
                CodeField.PASSWORD_CONFIRM_FIELD.code,
                ResponseErrorField.ERROR_PASSWORD_DOESNT_MATCH.label
            )
            validPasswordConfirm.value = 0
            changeEnableButton()
        }
    }

    private fun isValidLong(text: Editable?, code: Int, minValue: Int, validItem: MutableLiveData<Int>) {
        if (UtilsFields().isValidLong(text.toString(), minValue)) {
            setErrorText(code, ResponseErrorField.DEFAULT.label)
            validItem.value = 1
            changeEnableButton()
        } else {
            setErrorText(
                code,
                ResponseErrorField.ERROR_LONG_CHARACTERS.label + minValue + ResponseErrorField.ERROR_CHARACTERS.label
            )
            validItem.value = 0
            changeEnableButton()
        }
    }

    private fun isValidEmail(text: Editable?) {
        if (UtilsFields().isValidEmail(text.toString())) {
            setErrorText(CodeField.EMAIL_FIELD.code, ResponseErrorField.DEFAULT.label)
            editTextEmailDrawable.value = R.drawable.input_successful
            validEmail.value = 1
            changeEnableButton()
        } else {
            setErrorText(CodeField.EMAIL_FIELD.code, ResponseErrorField.ERROR_INVALID_MAIL.label)
            editTextEmailDrawable.value = R.drawable.input_error
            validEmail.value = 0
            changeEnableButton()
        }
    }

    private fun changeEnableButton() {
        if (signUpUseCase.changeEnableButton(
                validEmail.value!!,
                validPassword.value!!,
                validPasswordConfirm.value!!
            )
        ) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    private fun setErrorText(field: Int, value: String) {
        when (field) {
            CodeField.EMAIL_FIELD.code -> errorEmail.value = value
            CodeField.PASSWORD_FIELD.code -> errorPassword.value = value
            CodeField.PASSWORD_CONFIRM_FIELD.code -> errorPasswordConfirm.value = value
        }
    }

    fun navigateToLogIn() {
        navigateToLogIn.value = true
    }

    fun showPassword(field: Int){
        when (field) {
            CodeField.PASSWORD_FIELD.code -> {
                passwordCounter.value = passwordCounter.value!! + 1
                showPassword.value = UtilsFields().isNumberPair(passwordCounter.value!!)
            }
            CodeField.PASSWORD_CONFIRM_FIELD.code -> {
                passwordConfirmCounter.value = passwordConfirmCounter.value!! + 1
                showPasswordConfirm.value = UtilsFields().isNumberPair(passwordConfirmCounter.value!!)
            }
        }
    }


}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: SignUpViewModelFactory? = null
        fun getInstance(): SignUpViewModelFactory = instance ?: synchronized(this) {
            instance ?: SignUpViewModelFactory()
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel() as T
    }
}