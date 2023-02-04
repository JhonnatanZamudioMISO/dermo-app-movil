package com.miso.dermoapp.data.attributes.user.datasource

import com.miso.dermoapp.data.attributes.user.entitie.RequestUser
import com.miso.dermoapp.data.attributes.user.entitie.ResponseUser
import com.miso.dermoapp.data.attributes.user.source.UserApiPatient
import com.miso.dermoapp.data.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.datasource
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 9:12 a. m.
 * All rights reserved 2023.
 ****/

class UserDataSourceRemote {

    private val retrofit = RetrofitHelper.getRetrofit()
    private val response = retrofit.create(UserApiPatient::class.java)

    suspend fun insertUser(requestUsers: RequestUser): ResponseUser {
        return withContext(Dispatchers.IO) {
            response.insertUser(UUID.randomUUID().toString(),requestUsers,).body() ?: ResponseUser("Error inesperado","")
        }
    }
}