package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:17 p. m.
 * All rights reserved 2023.
 ****/

enum class CodePatterns(val value: String) {
    EMAIL_VALIDATION("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
}