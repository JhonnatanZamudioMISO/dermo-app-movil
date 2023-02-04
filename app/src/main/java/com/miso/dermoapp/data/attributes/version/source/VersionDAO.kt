package com.miso.dermoapp.data.attributes.version.source

import androidx.room.*
import com.miso.dermoapp.data.attributes.version.entitie.Version

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.version.source
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 8:16 p. m.
 * All rights reserved 2023.
 ****/

@Dao
interface VersionDAO {
    @Query("SELECT * FROM version ORDER BY versionCode DESC LIMIT 1")
    suspend fun lastVersion(): List<Version>

    @Insert
    suspend fun insertVersion(version: Version)

    @Update
    suspend fun updateVersion(version: Version)

    @Delete
    suspend fun deleteVersion(version: Version)

    @Query("DELETE FROM version")
    suspend fun clearVersions()
}