package com.miso.dermoapp.data.attributes.injury.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.entitie
 * Created by Jhonnatan E. Zamudio P. on 11/03/2023 at 11:54 p. m.
 * All rights reserved 2023.
 ****/

data class RequestInjury(
    @SerializedName("correo_electronico") val account: String,
    @SerializedName("tipo_de_lesion") val typeOfInjury: String,
    @SerializedName("forma_de_lesion") val shapeOfInjury: String,
    @SerializedName("numero_de_lesiones") val numberofInjuries: String,
    @SerializedName("distribucion") val distribution: String,
    @SerializedName("foto_de_lesion") val photoOfInjury: String
)