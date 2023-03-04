package com.miso.dermoapp.domain.models.entities

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.entities
 * Created by Jhonnatan E. Zamudio P. on 4/03/2023 at 7:17 a. m.
 * All rights reserved 2023.
 ****/

data class UserProfileDermatological(
    var email: String,
    var name: String,
    var age: String,
    var city: String,
    var typeKin: String,
    var photoTypeKin: String
)
