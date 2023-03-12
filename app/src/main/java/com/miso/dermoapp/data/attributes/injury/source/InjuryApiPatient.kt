package com.miso.dermoapp.data.attributes.injury.source

import com.miso.dermoapp.data.attributes.injury.entitie.RequestInjury
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjuries
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjury
import retrofit2.Response
import retrofit2.http.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.source
 * Created by Jhonnatan E. Zamudio P. on 11/03/2023 at 11:53 p. m.
 * All rights reserved 2023.
 ****/

interface InjuryApiPatient {
    @POST("injury/create")
    suspend fun insertInjury(@Header("dermo-traceability-id") uuid: String, @Body requestInjury: RequestInjury): Response<ResponseInjury>

    @GET("injury/getall")
    suspend fun getInjuriesByAccount(@Header("dermo-traceability-id") uuid: String, @Query("correoElectronico") account: String): Response<ResponseInjuries>
}