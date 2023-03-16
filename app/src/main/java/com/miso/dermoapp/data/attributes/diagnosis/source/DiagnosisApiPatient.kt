package com.miso.dermoapp.data.attributes.diagnosis.source

import com.miso.dermoapp.data.attributes.diagnosis.entitie.ResponseDiagnosis
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjuries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.diagnosis.source
 * Created by Jhonnatan E. Zamudio P. on 13/03/2023 at 12:26 a. m.
 * All rights reserved 2023.
 ****/

interface DiagnosisApiPatient {
    @GET("diagnostic/injury/{id}")
    suspend fun getDiagnosisById(@Path("id") id: String): Response<ResponseDiagnosis>
}
