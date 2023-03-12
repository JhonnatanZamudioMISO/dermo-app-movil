package com.miso.dermoapp.domain.useCases

import android.content.Context
import com.miso.dermoapp.data.attributes.injury.entitie.Injuries
import com.miso.dermoapp.data.attributes.injury.repository.InjuryRepository
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 12:41 a. m.
 * All rights reserved 2023.
 ****/

class InjuryUseCase(val injuryRepository: InjuryRepository) {

    suspend fun getDataInjuries(context: Context): List<Injuries> {
        return injuryRepository.getInjuriesByAccountRemote(getEmail(context)).injuries
    }

   private fun getEmail(context: Context): String {
        return sharedPreferences().get(context, KeySharedPreferences.EMAIL.value)
    }
}