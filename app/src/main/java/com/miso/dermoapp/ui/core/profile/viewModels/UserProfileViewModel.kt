package com.miso.dermoapp.ui.core.profile.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.data.attributes.city.repository.CityRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.profile.viewModels
 * Created by Jhonnatan E. Zamudio P. on 23/02/2023 at 9:05 p. m.
 * All rights reserved 2023.
 ****/

class UserProfileViewModel(cityRepository: CityRepository): ViewModel() {
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