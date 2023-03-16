package com.miso.dermoapp.data.attributes.profileDermatological.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.profileDermatological.entitie
 * Created by Jhonnatan E. Zamudio P. on 4/03/2023 at 7:52 a. m.
 * All rights reserved 2023.
 ****/

data class RequestProfileDermatological (
    @SerializedName("correo_electronico") val email: String,
    @SerializedName("nombre") val name: String,
    @SerializedName("edad") val age: String,
    @SerializedName("ciudad") val city: String,
    @SerializedName("tipo_de_piel") val typeOfKin: String,
    @SerializedName("foto_de_piel") val pathTypeOfKin: String
)