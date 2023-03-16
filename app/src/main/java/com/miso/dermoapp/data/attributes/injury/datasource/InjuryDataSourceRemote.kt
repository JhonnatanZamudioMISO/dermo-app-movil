package com.miso.dermoapp.data.attributes.injury.datasource

import com.miso.dermoapp.data.attributes.injury.entitie.RequestInjury
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjuries
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjury
import com.miso.dermoapp.data.attributes.injury.source.InjuryApiPatient
import com.miso.dermoapp.data.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.datasource
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 12:07 a. m.
 * All rights reserved 2023.
 ****/

class InjuryDataSourceRemote {

    private val retrofit = RetrofitHelper.getRetrofit()
    private val response = retrofit.create(InjuryApiPatient::class.java)

    suspend fun insertInjury(requestInjury: RequestInjury): ResponseInjury {
        return withContext(Dispatchers.IO) {
            val result = response.insertInjury(UUID.randomUUID().toString(), requestInjury)
            if (result.code() == 201)
                return@withContext ResponseInjury(result.body()!!.description,result.body()!!.created_at, result.body()!!.updated_at)
            else
                return@withContext ResponseInjury("Error inesperado","","")
        }
    }

    suspend fun getInjuriesByAccount(account: String): ResponseInjuries {
        return withContext(Dispatchers.IO) {
            val result = response.getInjuriesByAccount(UUID.randomUUID().toString(),account)
            if (result.code() == 200)
                return@withContext ResponseInjuries(result.body()!!.injuries,result.body()!!.description,result.body()!!.created_at, result.body()!!.updated_at)
            else
                return@withContext ResponseInjuries(emptyList(),"","","")
        }
    }
}