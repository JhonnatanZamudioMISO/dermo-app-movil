package com.miso.dermoapp.data.attributes.version.datasource

import com.miso.dermoapp.data.attributes.version.entitie.Version
import com.miso.dermoapp.data.attributes.version.source.VersionDAO

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.version.datasource
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 8:18 p. m.
 * All rights reserved 2023.
 ****/

class VersionDataSourceLocal private constructor(private val versionDAO: VersionDAO){

    companion object{
        private var INSTANCE: VersionDataSourceLocal? = null
        fun getInstance(versionDAO: VersionDAO): VersionDataSourceLocal =
            INSTANCE ?: VersionDataSourceLocal(versionDAO)
    }

    suspend fun queryLastVersion(): List<Version> = versionDAO.lastVersion()

    suspend fun insertVersion(version: Version) = versionDAO.insertVersion(version)

    suspend fun updateVersion(version: Version) = versionDAO.updateVersion(version)

    suspend fun deleteVersion(version: Version) = versionDAO.deleteVersion(version)

    suspend fun clearVersions() = versionDAO.clearVersions()
}