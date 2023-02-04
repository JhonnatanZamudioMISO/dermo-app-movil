package com.miso.dermoapp.domain.models.utils

import com.miso.dermoapp.domain.models.enumerations.CodePatterns

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.utils
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:16 p. m.
 * All rights reserved 2023.
 ****/

class UtilsFields {
    fun areFieldsEmpty(text: String): Boolean {
        return text.isEmpty()
    }

    fun isValidEmail(text: String): Boolean {
        return text.matches(CodePatterns.EMAIL_VALIDATION.value.toRegex())
    }

    fun isValidLong(text: String, min: Int): Boolean {
        return text.length > min
    }

    fun isNumberPair(number: Int): Boolean {
        return number % 2 == 0
    }
}