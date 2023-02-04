package com.miso.dermoapp.data.attributes.user.repository;

import kotlin.jvm.Volatile;

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.repository
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 9:05 a. m.
 * All rights reserved 2023.
 ****/

class UserRepository(private val userDataSourceRemote: UserDataSourceRemote) : UserRepositoryInterface {

    companion object {
        @Volatile
        private var instance: UserRepository? = null
            fun getInstance(
                userDataSourceRemote: UserDataSourceRemote,
                userDataSourceLocal: UserDataSourceLocal
        ): UserRepository =
        instance ?: synchronized(this) {
        instance ?: UserRepository(userDataSourceRemote,userDataSourceLocal)
        }
        }

        override suspend fun getUsersRemote(): List<ResponseUsers> {
        return userDataSourceRemote.getUsers()
        }

        override suspend fun getUserByAccountRemote(account: String): List<ResponseUsers> {
        return userDataSourceRemote.getUserByAccount(account)
        }

        override suspend fun insertUserRemote(requestUsers: RequestUsers): List<ResponseUsers> {
        return userDataSourceRemote.insertUser(requestUsers)
        }

        override suspend fun updateUserRemote(
        account: String,
        requestUsersUpdate: RequestUsersUpdate
        ): List<ResponseUsers> {
        return userDataSourceRemote.updateUser(account, requestUsersUpdate)
        }

        override suspend fun deleteUserRemote(account: String): List<ResponseUsers> {
        return userDataSourceRemote.deleteUser(account)
        }

        override suspend fun getAllUsersLocal(): List<User> {
        return userDataSourceLocal.getAllUsers()
        }

        override suspend fun getUserByAccountLocal(account: String): List<User> {
        return userDataSourceLocal.getUserByAccount(account)
        }

        override suspend fun insertUserLocal(user: User) {
        userDataSourceLocal.insertUser(user)
        }

        override suspend fun updateUserLocal(user: User) {
        userDataSourceLocal.updateUser(user)
        }

        override suspend fun deleteUserLocal(user: User) {
        userDataSourceLocal.deleteUser(user)
        }

        override suspend fun clearUsersLocal() {
        userDataSourceLocal.clearUsers()
        }

}
