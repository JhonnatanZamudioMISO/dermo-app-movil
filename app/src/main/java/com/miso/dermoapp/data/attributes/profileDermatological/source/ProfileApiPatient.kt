package com.miso.dermoapp.data.attributes.profileDermatological.source

import com.miso.dermoapp.data.attributes.profileDermatological.entitie.RequestProfileDermatological
import com.miso.dermoapp.data.attributes.profileDermatological.entitie.ResponseProfileDermatological
import retrofit2.Response
import retrofit2.http.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.source
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 9:22 a. m.
 * All rights reserved 2023.
 ****/

interface ProfileApiPatient {
    @POST("user/profile/create")
    suspend fun insertProfile(@Header("dermo-traceability-id") uuid: String,@Body requestProfileDermatological: RequestProfileDermatological): Response<ResponseProfileDermatological>
}