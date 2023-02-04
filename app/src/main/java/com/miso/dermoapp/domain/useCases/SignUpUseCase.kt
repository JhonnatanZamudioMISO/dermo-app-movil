package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.user.entitie.RequestUser
import com.miso.dermoapp.data.attributes.user.repository.UserRepository

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:05 p. m.
 * All rights reserved 2023.
 ****/

class SignUpUseCase(private val userRepository: UserRepository) {

    fun changeEnableButton(
        email: Int, password: Int, confirmPassword: Int, terms: Boolean
    ): Boolean {
        return email == 1 && password == 1 && confirmPassword == 1 && terms == true
    }

    fun arePasswordsEqual(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    suspend fun createUser(user: RequestUser): Int{
        /*val resultUser = userRepository.insertUserRemote(user)
        if (resultUser.first().message!! == "El usuario fue creado exitosamente"){
            if (user.statusUser== CodeStatusUser.ENABLED_USER.code){
                return 0
            } else if (user.statusUser== CodeStatusUser.UNVALIDATED_USER.code){
                return 3
            }
        } else if (resultUser.first().message!! == "Email erroneo...") {
            return 4
        }*/
        return 5
    }
}