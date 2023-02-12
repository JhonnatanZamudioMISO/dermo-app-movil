package com.miso.dermoapp.ui.core.session.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.data.attributes.user.repository.UserRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import kotlinx.coroutines.DelicateCoroutinesApi

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.session.viewModels
 * Created by Jhonnatan E. Zamudio P. on 12/02/2023 at 1:20 p. m.
 * All rights reserved 2023.
 ****/

class LogInViewModel(userRepository: UserRepository): ViewModel() {

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