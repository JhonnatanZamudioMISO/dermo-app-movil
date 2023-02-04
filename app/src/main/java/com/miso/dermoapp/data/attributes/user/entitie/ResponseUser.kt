package com.miso.dermoapp.data.attributes.user.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.entitie
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 9:09 a. m.
 * All rights reserved 2023.
 ****/

data class ResponseUser(
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val created_at: String
)