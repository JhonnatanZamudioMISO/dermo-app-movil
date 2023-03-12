package com.miso.dermoapp.data.attributes.injury.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.entitie
 * Created by Jhonnatan E. Zamudio P. on 11/03/2023 at 11:54 p. m.
 * All rights reserved 2023.
 ****/

data class ResponseInjuries  (
    @SerializedName("lesiones") val injuries: List<RequestInjury>,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)