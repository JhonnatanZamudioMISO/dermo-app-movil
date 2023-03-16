package com.miso.dermoapp.data.attributes.profileDermatological.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.profileDermatological.entitie
 * Created by Jhonnatan E. Zamudio P. on 4/03/2023 at 8:07 a. m.
 * All rights reserved 2023.
 ****/

data class ResponseProfileDermatological (
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)