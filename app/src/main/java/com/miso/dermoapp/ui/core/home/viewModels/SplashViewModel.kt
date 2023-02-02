package com.miso.dermoapp.ui.core.home.viewModels

import android.Manifest
import android.content.Context
import androidx.lifecycle.*
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.miso.dermoapp.data.attributes.version.repository.VersionRepository
import com.miso.dermoapp.domain.injectionOfDependencies.Injection
import com.miso.dermoapp.domain.models.enumerations.CodePermissions
import com.miso.dermoapp.domain.models.utils.UtilsNetwork
import com.miso.dermoapp.domain.useCases.SplashUseCase
import kotlinx.coroutines.*
import pub.devrel.easypermissions.EasyPermissions

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
    val snackBarTextCloseApp = MutableLiveData<String>()
    val isConected = MutableLiveData<Boolean>()
    val typePermission = MutableLiveData<String>()
    val codPermission = MutableLiveData<Int>()
    val messagePermission = MutableLiveData<String>()
    val requestPermission = MutableLiveData<Boolean>()
    val startUpdateFlow = MutableLiveData<Boolean>()
    val appUpdateInfoPlayStore = MutableLiveData<AppUpdateInfo>()

    init {
        GlobalScope.launch {
            loading.postValue(true)
            withContext(Dispatchers.IO) {
                getAppVersion()
                validatePermissions.postValue(true)
            }
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

    fun checkOnline(context: Context) {
        isConected.postValue(UtilsNetwork().isOnline(context))
    }

    fun hasPermission(context: Context, permission: String) {
        typePermission.value = permission
        codPermission.value = splashUseCase.getCodePermission(permission)
        messagePermission.value = splashUseCase.getMessagePermission(permission, context)
        when (EasyPermissions.hasPermissions(context, permission)) {
            true -> when (codPermission.value) {
                CodePermissions.CAMERA.code -> checkOnline(context)
                CodePermissions.WRITE_STORAGE.code -> hasPermission(
                    context,
                    Manifest.permission.CAMERA
                )
            }
            false -> requestPermission.postValue(true)
        }
    }

    fun checkUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateInfoPlayStore.value = appUpdateInfo
        startUpdateFlow.postValue(splashUseCase.shouldBeUpdated(appUpdateInfo))
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