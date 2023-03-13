package com.miso.dermoapp.data.attributes.injury.repository

import com.miso.dermoapp.data.attributes.injury.datasource.InjuryDataSourceRemote
import com.miso.dermoapp.data.attributes.injury.entitie.RequestInjury
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjuries
import com.miso.dermoapp.data.attributes.injury.entitie.ResponseInjury

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.injury.repository
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 12:16 a. m.
 * All rights reserved 2023.
 ****/

class InjuryRepository(private val injuryDataSourceRemote: InjuryDataSourceRemote) :
    InjuryRepositoryInterface {
    companion object {
        @Volatile
        private var instance: InjuryRepository? = null
        fun getInstance(
            injuryDataSourceRemote: InjuryDataSourceRemote
        ): InjuryRepository =
            instance ?: synchronized(this) {
                instance ?: InjuryRepository(injuryDataSourceRemote)
            }
    }
    override suspend fun insertInjuryRemote(requestInjury: RequestInjury): ResponseInjury {
        return injuryDataSourceRemote.insertInjury(requestInjury)
    }

    override suspend fun getInjuriesByAccountRemote(account: String): ResponseInjuries {
        return injuryDataSourceRemote.getInjuriesByAccount(account)
    }
}