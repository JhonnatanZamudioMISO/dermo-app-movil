package com.miso.dermoapp.data.attributes.diagnosis.datasource

import com.miso.dermoapp.data.attributes.diagnosis.entitie.ResponseDiagnosis
import com.miso.dermoapp.data.attributes.diagnosis.source.DiagnosisApiPatient
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

class DiagnosisDataSourceRemote {

    private val retrofit = RetrofitHelper.getRetrofitWEB()
    private val response = retrofit.create(DiagnosisApiPatient::class.java)

    suspend fun getDiagnosisById(id: String): ResponseDiagnosis {
        return withContext(Dispatchers.IO) {
            val result = response.getDiagnosisById(id)
            if (result.code() == 200)
                return@withContext ResponseDiagnosis(result.body()!!.condition,
                    result.body()!!.level,
                    result.body()!!.requeresTreatment,
                    result.body()!!.treatmentTerm,
                    result.body()!!.medicines,
                    result.body()!!.treatmentControl,
                    result.body()!!.recommendations)
            else
                return@withContext ResponseDiagnosis("","","","","","","")
        }
    }
}