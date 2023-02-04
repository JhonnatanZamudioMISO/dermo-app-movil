package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:01 p. m.
 * All rights reserved 2023.
 ****/

enum class ResponseErrorField (val value: String){
    DEFAULT(""),
    ERROR_EMPTY("El campo está vacío"),
    ERROR_INVALID_MAIL("Ingresa un correo válido"),
    ERROR_LONG_CHARACTERS("Debe ser mayor a "),
    ERROR_CHARACTERS(" caracteres"),
    ERROR_PASSWORD_DOESNT_MATCH("Las contraseñas no coinciden")
}