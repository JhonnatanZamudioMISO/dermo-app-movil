package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 14/02/2023 at 9:21 p. m.
 * All rights reserved 2023.
 ****/

enum class MessageResponseLoginUser (val value: String) {
    INICIO_DE_SESION_EXITOSO("Inicio de sesion exitoso"),
    LA_CUENTA_NO_EXISTE("La cuenta no existe"),
    CREDENCIALES_INVALIDAS("Credenciales invalidas")
}