package com.miso.dermoapp.domain.models.entities

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.entities
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:11 p. m.
 * All rights reserved 2023.
 ****/

data class UserAccountData(
    var id: String,
    var name: String,
    var email: String,
    var password: String,
    var passwordConfirm: String
)
