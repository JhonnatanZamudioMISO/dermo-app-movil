package com.miso.dermoapp.data.attributes.diagnosis.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.entitie
 * Created by Jhonnatan E. Zamudio P. on 11/03/2023 at 11:54 p. m.
 * All rights reserved 2023.
 ****/

data class ResponseDiagnosis  (
    @SerializedName("condition") val condition: String,
    @SerializedName("level") val level: String,
    @SerializedName("requeresTreatment") val requeresTreatment: String,
    @SerializedName("treatmentTerm") val treatmentTerm: String,
    @SerializedName("medicines") val medicines: String,
    @SerializedName("treatmentControl") val treatmentControl: String,
    @SerializedName("recommendations") val recommendations: String
)