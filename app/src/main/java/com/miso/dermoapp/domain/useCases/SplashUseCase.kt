package com.miso.dermoapp.domain.useCases

import android.Manifest
import com.miso.dermoapp.BuildConfig
import com.miso.dermoapp.data.attributes.version.entitie.Version
import com.miso.dermoapp.data.attributes.version.repository.VersionRepository
import com.miso.dermoapp.domain.models.enumerations.CodePermissions
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 12:46 p. m.
 * All rights reserved 2023.
 ****/

class SplashUseCase(val versionRepository: VersionRepository) {
    suspend fun getAppVersion():String {
        val versionQuery = versionRepository.queryLastVersionLocal()
        val versionName =
            if (versionQuery.size == 1 && versionQuery.equals(BuildConfig.VERSION_NAME))
                versionRepository.queryLastVersionLocal()[0].versionName
            else
                insertAppVersionDatabase()
        return "Versión $versionName"
    }

    private suspend fun insertAppVersionDatabase(): String {
        val versionName = BuildConfig.VERSION_NAME
        versionRepository.insertVersionLocal(
            Version(
                0,
                BuildConfig.VERSION_CODE,
                versionName,
                Calendar.getInstance().time
            )
        )
        return versionName
    }

    fun getCodePermission(permission: String): Int {
        var codePermission = CodePermissions.DEFAULT.code
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            codePermission = CodePermissions.WRITE_STORAGE.code
        return codePermission
    }
}