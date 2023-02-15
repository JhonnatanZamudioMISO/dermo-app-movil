package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.user.entitie.RequestUser
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

    suspend fun loginUser(user: RequestUser): Int{
        val resultUser = userRepository.getUserByAccountRemote(user.account,user.passwordUser)
        println("SE IMPRIME RESULTADO: " + resultUser)
        /*if (resultUser.description == "Cuenta creada exitosamente"){
            return 0
        } else if (resultUser.description == "El correo ingresado ya esta registrado") {
            return 1
        }
        println("EERORCONSUMO: " +resultUser.description)
         */
        return 2
    }
}