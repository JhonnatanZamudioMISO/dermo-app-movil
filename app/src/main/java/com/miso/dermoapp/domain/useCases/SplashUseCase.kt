package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.BuildConfig
import com.miso.dermoapp.data.attributes.version.entitie.Version
import com.miso.dermoapp.data.attributes.version.repository.VersionRepository
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 12:46 p. m.
 * All rights reserved 2023.
 ****/

class SplashUseCase(val versionRepository: VersionRepository) {
    suspend fun getAppVersion():String {
        var versionName = ""
        val version  = versionRepository.queryLastVersionLocal()
        version.map { x ->
            versionName = x.versionName
        }
        if (versionName.equals("")){
            versionName = BuildConfig.VERSION_NAME
            versionRepository.insertVersionLocal(
                Version(
                0,
                BuildConfig.VERSION_CODE,
                versionName,
                Calendar.getInstance().time)
            )
        }
        return "Versión " + versionName
    }
}