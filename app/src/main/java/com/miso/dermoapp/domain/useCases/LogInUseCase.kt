package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.user.entitie.RequestUser
import com.miso.dermoapp.data.attributes.user.repository.UserRepository
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.domain.models.enumerations.MessageResponseLoginUser

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
        if (resultUser.description == MessageResponseLoginUser.INICIO_DE_SESION_EXITOSO.value){
            return CodeResponseLoginUser.INICIO_DE_SESION_EXITOSO.code
        } else if (resultUser.description == MessageResponseLoginUser.LA_CUENTA_NO_EXISTE.value) {
            return CodeResponseLoginUser.LA_CUENTA_NO_EXISTE.code
        } else if (resultUser.description == MessageResponseLoginUser.CREDENCIALES_INVALIDAS.value) {
            return CodeResponseLoginUser.CREDENCIALES_INVALIDAS.code
        }
        return CodeResponseLoginUser.ERROR.code
    }
}