package com.miso.dermoapp.domain.useCases

import android.content.Context
import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType
import com.miso.dermoapp.data.attributes.typeKin.repository.TypeKinRepository
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 6:18 a. m.
 * All rights reserved 2023.
 ****/

class UserDermatologicalUseCase(private val typeKinRepository: TypeKinRepository) {

    suspend fun getDataTypeKin(): List<ResponseKinType> {
        return typeKinRepository.getDataTypeKin().sortedBy { myObject -> myObject.abbreviate }
    }

    fun getEmail(context: Context): String {
        return sharedPreferences().get(context, KeySharedPreferences.EMAIL.value)
    }
}