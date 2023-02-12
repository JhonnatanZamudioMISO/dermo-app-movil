package com.miso.dermoapp.ui.core.session.viewModels

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.user.repository.UserRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.models.entities.UserAccountData
import com.miso.dermoapp.domain.models.enumerations.CodeField
import com.miso.dermoapp.domain.models.enumerations.CodeLong
import com.miso.dermoapp.domain.models.enumerations.CodeSnackBarCloseAction
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.domain.models.utils.UtilsFields
import com.miso.dermoapp.domain.models.utils.UtilsNetwork
import com.miso.dermoapp.domain.useCases.LogInUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.session.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/02/2023 at 1:20 p. m.
 * All rights reserved 2023.
 ****/

class LogInViewModel(userRepository: UserRepository): ViewModel() {
    val errorEmail = MutableLiveData<String>()
    val errorPassword = MutableLiveData<String>()
    private var validEmail = MutableLiveData<Int>()
    private var validPassword = MutableLiveData<Int>()
    private val logInUseCase = LogInUseCase(userRepository)
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    var userAccount = MutableLiveData<UserAccountData>()
    val editTextEmailDrawable = MutableLiveData<Int>()
    val editTextPasswordDrawable = MutableLiveData<Int>()
    private var passwordCounter = MutableLiveData<Int>()
    var showPassword = MutableLiveData<Boolean>()
    val navigateToLogIn = MutableLiveData<Boolean>()
    val snackBarAction = MutableLiveData<Int>()
    val snackBarNavigate = MutableLiveData<Int>()
    val snackBarTextWarning = MutableLiveData<String>()

    init {
        errorEmail.value = ResponseErrorField.DEFAULT.label
        errorPassword.value = ResponseErrorField.DEFAULT.label
        validEmail.value = 0
        validPassword.value = 0
        userAccount.value = UserAccountData("","","","", "")
        passwordCounter.value = 0
        navigateToLogIn.value = false
        snackBarNavigate.value = CodeSnackBarCloseAction.NONE.code
    }
    fun areFieldsEmpty(text: Editable?, field: Int) {
        if (UtilsFields().areFieldsEmpty(text.toString())) {
            setErrorText(field, ResponseErrorField.ERROR_EMPTY.label)
            when (field) {
                CodeField.EMAIL_FIELD.code -> validEmail.value = 0
                CodeField.PASSWORD_FIELD.code -> validPassword.value = 0
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
                }
            }
        }
    }

    private fun isValidLong(text: Editable?, code: Int, minValue: Int, validItem: MutableLiveData<Int>) {
        if (UtilsFields().isValidLong(text.toString(), minValue)) {
            setErrorText(code, ResponseErrorField.DEFAULT.label)
            editTextPasswordDrawable.value = R.drawable.input_successful
            validItem.value = 1
            changeEnableButton()
        } else {
            setErrorText(
                code,
                ResponseErrorField.ERROR_LONG_CHARACTERS.label + minValue + ResponseErrorField.ERROR_CHARACTERS.label
            )
            validItem.value = 0
            editTextPasswordDrawable.value = R.drawable.input_error
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

    fun showPassword(field: Int){
        when (field) {
            CodeField.PASSWORD_FIELD.code -> {
                passwordCounter.value = passwordCounter.value!! + 1
                showPassword.value = UtilsFields().isNumberPair(passwordCounter.value!!)
            }
        }
    }

    fun LogIn() {
        navigateToLogIn.value = true
    }

    private fun setErrorText(field: Int, value: String) {
        when (field) {
            CodeField.EMAIL_FIELD.code -> errorEmail.value = value
            CodeField.PASSWORD_FIELD.code -> errorPassword.value = value
        }
        changeEnableButton()
    }

    private fun changeEnableButton() {
        if (logInUseCase.changeEnableButton(
                validEmail.value!!,
                validPassword.value!!
            )
        ) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    fun checkOnline(context: Context): Boolean {
        return UtilsNetwork().isOnline(context)
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class LogInViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: LogInViewModelFactory? = null
        fun getInstance(): LogInViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: LogInViewModelFactory(
                    Injection.providerUserRepository()
                )
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LogInViewModel(userRepository) as T
    }
}