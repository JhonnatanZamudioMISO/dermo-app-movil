package com.miso.dermoapp.data.attributes.user.repository;

import com.miso.dermoapp.data.attributes.user.datasource.UserDataSourceRemote;

import kotlin.jvm.Volatile;

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.repository
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 9:05 a. m.
 * All rights reserved 2023.
 ****/

class UserRepository(private val userDataSourceRemote:UserDataSourceRemote) : UserRepositoryInterface {

    companion object {
        @Volatile
        private var instance: UserRepository? = null
            fun getInstance(
                userDataSourceRemote: UserDataSourceRemote
        ): UserRepository =
        instance ?: synchronized(this) {
                instance ?: UserRepository(userDataSourceRemote)
                }
    }


        override suspend fun insertUserRemote(requestUsers: RequestUsers): List<ResponseUsers> {
        return userDataSourceRemote.insertUser(requestUsers)
        }

}
