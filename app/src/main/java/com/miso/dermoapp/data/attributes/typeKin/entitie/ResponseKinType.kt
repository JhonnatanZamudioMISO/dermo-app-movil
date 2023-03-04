package com.miso.dermoapp.data.attributes.typeKin.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.typeKin.entitie
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:16 a. m.
 * All rights reserved 2023.
 ****/

data class ResponseKinType(
    @SerializedName("id") val id: String,
    @SerializedName("descripcion") val description: String,
    @SerializedName("abreviatura") val abbreviate: String,
    @SerializedName("valor") val valor: Int
)