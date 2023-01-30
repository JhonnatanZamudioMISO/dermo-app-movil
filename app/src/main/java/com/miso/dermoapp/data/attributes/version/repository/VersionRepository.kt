package com.miso.dermoapp.data.attributes.version.repository

import com.miso.dermoapp.data.attributes.version.datasource.VersionDataSourceLocal
import com.miso.dermoapp.data.attributes.version.entitie.Version

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.version.repository
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 8:19 p. m.
 * All rights reserved 2023.
 ****/

class VersionRepository (private val versionDataSourceLocal: VersionDataSourceLocal) :
    VersionRepositoryInterface {

    companion object{
        @Volatile
        private var instance: VersionRepository? = null
        fun getInstance(versionDataLocal: VersionDataSourceLocal): VersionRepository =
            instance ?: synchronized(this){
                instance ?: VersionRepository(versionDataLocal)
            }
    }

    override suspend fun queryLastVersionLocal(): List<Version> {
        return versionDataSourceLocal.queryLastVersion()
    }

    override suspend fun insertVersionLocal(version: Version) {
        versionDataSourceLocal.insertVersion(version)
    }

    override suspend fun updateVersionLocal(version: Version) {
        versionDataSourceLocal.updateVersion(version)
    }

    override suspend fun deleteVersionLocal(version: Version) {
        versionDataSourceLocal.deleteVersion(version)
    }

    override suspend fun clearVersionsLocal() {
        versionDataSourceLocal.clearVersions()
    }
}