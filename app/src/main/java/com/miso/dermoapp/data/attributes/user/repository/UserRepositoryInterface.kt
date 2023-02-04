package com.miso.dermoapp.data.attributes.user.repository

import com.miso.dermoapp.data.attributes.user.entitie.RequestUser
import com.miso.dermoapp.data.attributes.user.entitie.ResponseUser

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.user.repository
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 9:07 a. m.
 * All rights reserved 2023.
 ****/

interface UserRepositoryInterface {
    suspend fun insertUserRemote(requestUsers: RequestUser): List<ResponseUser>
}