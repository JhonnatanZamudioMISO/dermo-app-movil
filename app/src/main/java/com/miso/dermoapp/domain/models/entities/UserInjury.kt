package com.miso.dermoapp.domain.models.entities

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.entities
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 9:42 p. m.
 * All rights reserved 2023.
 ****/

data class UserInjury(
    var email: String,
    var typeOfInjury: String,
    var formOfInjury: String,
    var numberOfTheInjuries: String,
    var distribution: String,
    var photoOfInjury: String
)
