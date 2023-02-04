package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 10:08 a. m.
 * All rights reserved 2023.
 ****/

enum class TypeSnackBar (val code: Int) {
    CLOSE_APP(0x1),
    ERROR(0X2),
    INFO(0x3),
    WARNING(0X4),
    SUCCESS(0X5)
}