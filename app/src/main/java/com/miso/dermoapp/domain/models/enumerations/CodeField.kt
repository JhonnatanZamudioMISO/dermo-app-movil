package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:19 p. m.
 * All rights reserved 2023.
 ****/

enum class CodeField (val code: Int) {
    EMAIL_FIELD(0x1),
    PASSWORD_FIELD(0x2),
    PASSWORD_CONFIRM_FIELD(0x3)
}