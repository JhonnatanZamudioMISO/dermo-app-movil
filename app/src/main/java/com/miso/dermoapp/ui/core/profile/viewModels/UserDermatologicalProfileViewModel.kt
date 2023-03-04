package com.miso.dermoapp.ui.core.profile.viewModels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.profileDermatological.entitie.RequestProfileDermatological
import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType
import com.miso.dermoapp.data.attributes.typeKin.repository.TypeKinRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.models.entities.UserProfileDermatological
import com.miso.dermoapp.domain.models.enumerations.CodeSnackBarCloseAction
import com.miso.dermoapp.domain.models.utils.UtilsNetwork
import com.miso.dermoapp.domain.useCases.UserDermatologicalUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.profile.viewModels
 * Created by Jhonnatan E. Zamudio P. on 28/02/2023 at 4:42 a. m.
 * All rights reserved 2023.
 ****/

class UserDermatologicalProfileViewModel(typeKinRepository: TypeKinRepository): ViewModel() {
    val typeKinSelectedPosition = MutableLiveData<Int>()
    lateinit var typeKinsList: List<ResponseKinType>
    private val userDermatologicalUseCase = UserDermatologicalUseCase(typeKinRepository)
    val statusPhoto = MutableLiveData<Boolean>()
    val buttonContinueDrawable = MutableLiveData<Int>()
    val buttonContinueEnable = MutableLiveData<Boolean>()
    private var validTipoDePiel = MutableLiveData<Int>()
    private var validFoto = MutableLiveData<Int>()
    val navigateToDashboard = MutableLiveData<Boolean>()
    val snackBarAction = MutableLiveData<Int>()
    val snackBarNavigate = MutableLiveData<Int>()
    val snackBarTextWarning = MutableLiveData<String>()
    var userProfileDermatological = MutableLiveData<UserProfileDermatological>()
    val resultCreateProfileDermatological = MutableLiveData<Int>()

    init {
        getKinTypeSpinner()
        statusPhoto.value = false
        validTipoDePiel.value = 0
        validFoto.value = 0
        navigateToDashboard.value = false
        snackBarNavigate.value = CodeSnackBarCloseAction.NONE.code
        userProfileDermatological.value = UserProfileDermatological("","","","","","")
        changeEnableButton()
    }

    fun getEmail(context: Context){
        userProfileDermatological.value!!.email = userDermatologicalUseCase.getEmail(context)
        userProfileDermatological.value!!.name = userDermatologicalUseCase.getName(context)
        userProfileDermatological.value!!.age = userDermatologicalUseCase.getAge(context)
        userProfileDermatological.value!!.city = userDermatologicalUseCase.getCity(context)
    }

    private fun getKinTypeSpinner() {
        viewModelScope.launch {
            typeKinsList = userDermatologicalUseCase.getDataTypeKin()
        }
    }

    fun AddPhoto() {
        statusPhoto.value = true
    }

    fun validateSpinner (typeSkin: String) {
        validTipoDePiel.value = 1
        userProfileDermatological.value!!.typeKin = typeSkin
        changeEnableButton()
    }

    private fun changeEnableButton() {
        if (userDermatologicalUseCase.changeEnableButton(
                validTipoDePiel.value!!,
                validFoto.value!!
            )
        ) {
            buttonContinueDrawable.value = R.drawable.boton_oscuro
            buttonContinueEnable.value = true
        } else {
            buttonContinueDrawable.value = R.drawable.boton_oscuro_disabled
            buttonContinueEnable.value = false
        }
    }

    fun validatePhoto(bitmap: Bitmap) {
        validFoto.value = 1
        userProfileDermatological.value!!.photoTypeKin = userDermatologicalUseCase.getBase64Photo(bitmap)
        changeEnableButton()
    }

    fun ContinueProfileDermatological() {
        navigateToDashboard.value = true
    }

    fun checkOnline(context: Context): Boolean {
        getEmail(context)
        return UtilsNetwork().isOnline(context)
    }

    fun createProfileDermatological() {
        viewModelScope.launch {
            val userProfile = RequestProfileDermatological(
                userProfileDermatological.value!!.email,
                userProfileDermatological.value!!.name,
                userProfileDermatological.value!!.age,
                userProfileDermatological.value!!.city,
                userProfileDermatological.value!!.typeKin,
                userProfileDermatological.value!!.photoTypeKin
            )
            resultCreateProfileDermatological.value = userDermatologicalUseCase.createProfile(userProfile)
        }
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class UserDermatologicalProfileViewModelFactory(
    private val typeKinRepository: TypeKinRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: UserDermatologicalProfileViewModelFactory? = null
        fun getInstance(context: Context): UserDermatologicalProfileViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: UserDermatologicalProfileViewModelFactory(
                    Injection.providerTypeKinRepository(context)
                )
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDermatologicalProfileViewModel(typeKinRepository) as T
    }
}