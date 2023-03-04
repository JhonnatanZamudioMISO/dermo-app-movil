package com.miso.dermoapp.domain.useCases

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import com.miso.dermoapp.data.attributes.profileDermatological.entitie.RequestProfileDermatological
import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType
import com.miso.dermoapp.data.attributes.typeKin.repository.TypeKinRepository
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import java.io.ByteArrayOutputStream


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

    fun getName(context: Context): String {
        return sharedPreferences().get(context, KeySharedPreferences.NAME.value)
    }

    fun getAge(context: Context): String {
        return sharedPreferences().get(context, KeySharedPreferences.AGE.value)
    }

    fun getCity(context: Context): String {
        return sharedPreferences().get(context, KeySharedPreferences.CITY.value)
    }

    fun changeEnableButton(typeKin: Int, photo: Int): Boolean {
        return typeKin == 1 && photo == 1
    }

    fun getBase64Photo(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    suspend fun createProfile(profile: RequestProfileDermatological): Int {
        /*val resultUser = userRepository.insertUserRemote(user)
        if (resultUser.description == "Cuenta creada exitosamente"){
            return 0
        } else if (resultUser.description == "El correo ingresado ya esta registrado") {
            return 1
        }*/
        return 2
    }
}