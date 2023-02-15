package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 14/02/2023 at 9:29 p. m.
 * All rights reserved 2023.
 ****/

enum class CodeResponseLoginUser (val code: Int) {
    INICIO_DE_SESION_EXITOSO(0x0),
    LA_CUENTA_NO_EXISTE(0x1),
    CREDENCIALES_INVALIDAS(0x2),
    ERROR(0x3)
}