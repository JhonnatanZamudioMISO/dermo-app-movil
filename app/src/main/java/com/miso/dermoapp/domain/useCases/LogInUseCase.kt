package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.user.repository.UserRepository

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 12/02/2023 at 2:06 p. m.
 * All rights reserved 2023.
 ****/

class LogInUseCase(private val userRepository: UserRepository) {
    fun changeEnableButton(email: Int, password: Int): Boolean {
        return email == 1 && password == 1
    }
}