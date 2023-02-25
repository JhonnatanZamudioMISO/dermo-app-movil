package com.miso.dermoapp.domain.injectionOfDependencies

import android.content.Context
import com.miso.dermoapp.data.attributes.city.datasource.CityDataSourceLocal
import com.miso.dermoapp.data.attributes.city.repository.CityRepository
import com.miso.dermoapp.data.attributes.city.source.CityJSON
import com.miso.dermoapp.data.attributes.user.datasource.UserDataSourceRemote
import com.miso.dermoapp.data.attributes.user.repository.UserRepository
import com.miso.dermoapp.data.attributes.version.datasource.VersionDataSourceLocal
import com.miso.dermoapp.data.attributes.version.repository.VersionRepository
import com.miso.dermoapp.data.room.DermoAppDB

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.injectionOfDependencies
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 12:48 p. m.
 * All rights reserved 2023.
 ****/

object Injection {
    fun providerVersionRepository(context: Context): VersionRepository {
        val database = DermoAppDB.getInstance(context)
        val splashScreenDataSource = VersionDataSourceLocal.getInstance(database.versionDAO())
        return VersionRepository.getInstance(splashScreenDataSource)
    }

    fun providerUserRepository(): UserRepository {
        val userDataSourceRemote = UserDataSourceRemote()
        return UserRepository.getInstance(userDataSourceRemote)
    }

    fun providerCityRepository(context:Context): CityRepository {
        val citiesJSON = CityJSON(context)
        val citiesDataSourceLocal = CityDataSourceLocal(citiesJSON)
        return CityRepository.getInstance(citiesDataSourceLocal)
    }
}