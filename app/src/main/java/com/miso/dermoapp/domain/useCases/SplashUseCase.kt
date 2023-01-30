package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.version.repository.VersionRepository

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 12:46 p. m.
 * All rights reserved 2023.
 ****/

class SplashUseCase(val versionRepository: VersionRepository) {
    suspend fun getAppVersion():String {
        val version = versionRepository.queryLastVersionLocal()
        return version.toString()
    }
}