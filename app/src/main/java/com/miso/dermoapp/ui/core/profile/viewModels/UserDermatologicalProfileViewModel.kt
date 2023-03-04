package com.miso.dermoapp.ui.core.profile.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType
import com.miso.dermoapp.data.attributes.typeKin.repository.TypeKinRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
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
    val email = MutableLiveData<String>()

    init {
        getKinTypeSpinner()
        statusPhoto.value = false
    }

    fun getEmail(context: Context){
        email.postValue(userDermatologicalUseCase.getEmail(context))
    }

    private fun getKinTypeSpinner() {
        viewModelScope.launch {
            typeKinsList = userDermatologicalUseCase.getDataTypeKin()
        }
    }

    fun AddPhoto() {
        statusPhoto.value = true
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