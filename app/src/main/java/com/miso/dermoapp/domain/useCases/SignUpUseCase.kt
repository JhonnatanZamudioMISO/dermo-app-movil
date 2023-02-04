package com.miso.dermoapp.domain.useCases

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:05 p. m.
 * All rights reserved 2023.
 ****/

class SignUpUseCase {

    fun changeEnableButton(
        email: Int, password: Int, confirmPassword: Int,
    ): Boolean {
        return email == 1 && password == 1 && confirmPassword == 1
    }

    fun arePasswordsEqual(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}