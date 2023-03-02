package com.miso.dermoapp.ui.core.profile.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.data.attributes.typeKin.repository.TypeKinRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.profile.viewModels
 * Created by Jhonnatan E. Zamudio P. on 28/02/2023 at 4:42 a. m.
 * All rights reserved 2023.
 ****/

class UserDermatologicalProfileViewModel(typeKinRepository: TypeKinRepository): ViewModel() {
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