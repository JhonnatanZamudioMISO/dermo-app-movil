package com.miso.dermoapp.data.attributes.injury.repository

import com.miso.dermoapp.data.attributes.injury.entitie.RequestInjury
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjuries
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjury

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.repository
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 12:16 a. m.
 * All rights reserved 2023.
 ****/

interface InjuryRepositoryInterface {
    suspend fun insertInjuryRemote(requestInjury: RequestInjury): ResponseInjury

    suspend fun getInjuriesByAccountRemote(account: String): ResponseInjuries
}