package com.miso.dermoapp.domain.useCases

import android.Manifest
import android.content.Context
import com.miso.dermoapp.BuildConfig
import com.miso.dermoapp.R
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
        return when (permission) {
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> CodePermissions.WRITE_STORAGE.code
            Manifest.permission.CAMERA -> CodePermissions.CAMERA.code
            else -> CodePermissions.DEFAULT.code
        }
    }

    fun getMessagePermission(permission: String, context: Context): String {
        return when (permission) {
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> context.getString(R.string.rationale_write_storage)
            Manifest.permission.CAMERA -> context.getString(R.string.rationale_camera)
            else -> context.getString(R.string.rationale_default)
        }
    }
}