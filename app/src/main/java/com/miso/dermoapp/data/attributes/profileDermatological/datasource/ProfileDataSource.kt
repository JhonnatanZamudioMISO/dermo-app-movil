package com.miso.dermoapp.data.attributes.profileDermatological.datasource

import com.miso.dermoapp.data.attributes.profileDermatological.entitie.RequestProfileDermatological
import com.miso.dermoapp.data.attributes.profileDermatological.entitie.ResponseProfileDermatological
import com.miso.dermoapp.data.attributes.profileDermatological.source.ProfileApiPatient
import com.miso.dermoapp.data.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.profileDermatological.datasource
 * Created by Jhonnatan E. Zamudio P. on 4/03/2023 at 8:52 a. m.
 * All rights reserved 2023.
 ****/

class ProfileDataSource {

    private val retrofit = RetrofitHelper.getRetrofit()
    private val response = retrofit.create(ProfileApiPatient::class.java)

    suspend fun insertProfile(requestProfileDermatological: RequestProfileDermatological): ResponseProfileDermatological {
        return withContext(Dispatchers.IO) {
            val result = response.insertProfile(UUID.randomUUID().toString(),requestProfileDermatological)
            if (result.code() == 201)
                return@withContext ResponseProfileDermatological(result.body()!!.description,result.body()!!.created_at, result.body()!!.updated_at)
            else
                return@withContext ResponseProfileDermatological("Error inesperado","","")
        }
    }
}