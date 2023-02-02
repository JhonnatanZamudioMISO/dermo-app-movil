package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.entities
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 7:44 a. m.
 * All rights reserved 2023.
 ****/

enum class CodePermissions(val code: Int) {
    DEFAULT(0x0),
    WRITE_STORAGE(0x1),
    CAMERA(0x2)
}