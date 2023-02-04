package com.miso.dermoapp.data.attributes.user.source

import com.miso.dermoapp.data.attributes.user.entitie.RequestUser
import com.miso.dermoapp.data.attributes.user.entitie.ResponseUser
import retrofit2.Response
import retrofit2.http.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.source
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 9:22 a. m.
 * All rights reserved 2023.
 ****/

interface UserApiPatient {
    @Headers("Content-Type: application/json")
    @POST("account/create")
    suspend fun insertUser(@Body requestUser: RequestUser): Response<ResponseUser>
}