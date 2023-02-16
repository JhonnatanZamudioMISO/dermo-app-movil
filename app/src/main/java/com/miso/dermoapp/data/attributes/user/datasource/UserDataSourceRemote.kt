package com.miso.dermoapp.data.attributes.user.datasource

import com.miso.dermoapp.data.attributes.user.entitie.RequestUser
import com.miso.dermoapp.data.attributes.user.entitie.ResponseUser
import com.miso.dermoapp.data.attributes.user.source.UserApiPatient
import com.miso.dermoapp.data.retrofit.RetrofitHelper
import com.miso.dermoapp.domain.models.enumerations.MessageResponseLoginUser
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
            val result = response.insertUser(UUID.randomUUID().toString(),requestUsers)
            if (result.code() == 201)
                return@withContext ResponseUser(result.body()!!.description,result.body()!!.created_at)
            else if (result.code() == 400)
                return@withContext ResponseUser("El correo ingresado ya esta registrado","")
            else
                return@withContext ResponseUser("Error inesperado","")
        }
    }

    suspend fun getUserByAccount(account: String, password: String): ResponseUser {
        return withContext(Dispatchers.IO) {
            val result = response.getUserByAccount(UUID.randomUUID().toString(),account,password)
            if (result.code() == 200)
                return@withContext ResponseUser(result.body()!!.description,result.body()!!.created_at)
            else if (result.code() == 404)
                return@withContext ResponseUser(MessageResponseLoginUser.LA_CUENTA_NO_EXISTE.value,"")
            else if (result.code() == 401)
                return@withContext ResponseUser(MessageResponseLoginUser.CREDENCIALES_INVALIDAS.value,"")
            else
                return@withContext ResponseUser("Error inesperado","")
        }
    }
}