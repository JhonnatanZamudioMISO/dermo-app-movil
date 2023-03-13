package com.miso.dermoapp.domain.useCases

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import com.miso.dermoapp.data.attributes.injury.entitie.RequestInjury
import com.miso.dermoapp.data.attributes.injury.repository.InjuryRepository
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import java.io.ByteArrayOutputStream

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 9:45 p. m.
 * All rights reserved 2023.
 ****/

class PhotoOfInjuryUseCase(val injuryRepository: InjuryRepository) {
    fun getBase64Photo(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun getDataPreferences(context: Context, value: String, ): String {
        return sharedPreferences().get(context, value)
    }

    suspend fun createInjury(injury: RequestInjury): Int {
        val resultInjury = injuryRepository.insertInjuryRemote(injury)
        if (resultInjury.description == "Lesion creada exitosamente")
            return CodeResponseLoginUser.CREAR_LESION.code
        return CodeResponseLoginUser.ERROR.code
    }
}