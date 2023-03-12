package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.injury.entitie.Injuries
import com.miso.dermoapp.data.attributes.injury.repository.InjuryRepository

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 12:41 a. m.
 * All rights reserved 2023.
 ****/

class InjuryUseCase(val injuryRepository: InjuryRepository) {

    suspend fun getDataInjuries(): List<Injuries> {
        return injuryRepository.getInjuriesByAccountRemote("mazf1006@gmail.com").injuries
    }
}