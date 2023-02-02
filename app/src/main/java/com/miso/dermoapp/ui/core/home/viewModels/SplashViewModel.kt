package com.miso.dermoapp.ui.core.home.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.miso.dermoapp.data.attributes.version.repository.VersionRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.useCases.SplashUseCase
import kotlinx.coroutines.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.presentation.core.home.viewModels
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 12:43 p. m.
 * All rights reserved 2023.
 ****/

@OptIn(DelicateCoroutinesApi::class)
class SplashViewModel(versionRepository: VersionRepository): ViewModel() {
    val version = MutableLiveData<String>()
    private val splashUseCase = SplashUseCase(versionRepository)
    val loading = MutableLiveData<Boolean>()
    val validatePermissions = MutableLiveData<Boolean>()

    init {
        GlobalScope.launch {
            loading.postValue(true)
            withContext(Dispatchers.IO) {
                getAppVersion()
                validatePermissions.postValue(true)
                //delay(1000)
            }
            //loading.postValue(false)
        }
    }

    fun setVersion(v:String){
        version.value = v
    }

    private fun getAppVersion() {
        viewModelScope.launch {
            setVersion(splashUseCase.getAppVersion())
        }
    }
}

@DelicateCoroutinesApi
@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory(
    private val versionRepository: VersionRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: SplashViewModelFactory? = null
        fun getInstance(context: Context): SplashViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SplashViewModelFactory(
                    Injection.providerVersionRepository(context)
                )
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(versionRepository) as T
    }
}