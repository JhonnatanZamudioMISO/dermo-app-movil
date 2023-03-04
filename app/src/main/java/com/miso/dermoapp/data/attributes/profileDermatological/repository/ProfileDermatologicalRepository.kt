package com.miso.dermoapp.data.attributes.profileDermatological.repository

import com.miso.dermoapp.data.attributes.profileDermatological.datasource.ProfileDataSource
import com.miso.dermoapp.data.attributes.profileDermatological.entitie.RequestProfileDermatological
import com.miso.dermoapp.data.attributes.profileDermatological.entitie.ResponseProfileDermatological

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.profileDermatological.repository
 * Created by Jhonnatan E. Zamudio P. on 4/03/2023 at 8:51 a. m.
 * All rights reserved 2023.
 ****/

class ProfileDermatologicalRepository(private val profileDataSourceRemote: ProfileDataSource) :
    ProfileDermatologicalRepositoryInterface {
    companion object {
        @Volatile
        private var instance: ProfileDermatologicalRepository? = null
        fun getInstance(
            profileDataSourceRemote: ProfileDataSource
        ): ProfileDermatologicalRepository =
            instance ?: synchronized(this) {
                instance ?: ProfileDermatologicalRepository(profileDataSourceRemote)
            }
    }

    override suspend fun insertCreateProfileRemote(requestProfile: RequestProfileDermatological): ResponseProfileDermatological {
        return profileDataSourceRemote.insertProfile(requestProfile)
    }
}