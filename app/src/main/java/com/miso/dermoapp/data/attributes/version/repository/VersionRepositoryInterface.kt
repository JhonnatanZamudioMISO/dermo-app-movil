package com.miso.dermoapp.data.attributes.version.repository

import com.miso.dermoapp.data.attributes.version.entitie.Version

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.version.repository
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 8:20 p. m.
 * All rights reserved 2023.
 ****/

interface VersionRepositoryInterface {
    suspend fun queryLastVersionLocal(): List<Version>

    suspend fun insertVersionLocal(version: Version)

    suspend fun updateVersionLocal(version: Version)

    suspend fun deleteVersionLocal(version: Version)

    suspend fun clearVersionsLocal()
}