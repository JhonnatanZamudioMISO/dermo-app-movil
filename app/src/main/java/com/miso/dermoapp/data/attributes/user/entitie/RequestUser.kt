package com.miso.dermoapp.data.attributes.user.entitie

import com.google.gson.annotations.SerializedName

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.entitie
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 8:54 a. m.
 * All rights reserved 2023.
 ****/

data class RequestUser(
    @SerializedName("account") val account: String,
    @SerializedName("password_user") val passwordUser: String
)