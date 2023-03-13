package com.miso.dermoapp.data.attributes.injury.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.entitie
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 12:39 a. m.
 * All rights reserved 2023.
 ****/

data class Injuries(
    @SerializedName("correo_electronico") var account: String,
    @SerializedName("tipo_de_lesion") var typeOfInjury: String,
    @SerializedName("forma_de_lesion") var shapeOfInjury: String,
    @SerializedName("numero_de_lesiones") var numberofInjuries: String,
    @SerializedName("distribucion") var distribution: String,
    @SerializedName("foto_de_lesion") var photoOfInjury: String,
    @SerializedName("created_at") var created_at: String,
)
