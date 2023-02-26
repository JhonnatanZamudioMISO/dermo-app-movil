package com.miso.dermoapp.domain.useCases

import android.content.Context
import com.miso.dermoapp.data.attributes.city.entitie.ResponseCities
import com.miso.dermoapp.data.attributes.city.repository.CityRepository
import com.miso.dermoapp.domain.models.entities.UserProfileData
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 1:35 p. m.
 * All rights reserved 2023.
 ****/

class UserProfileUseCase(val cityRepository: CityRepository) {
    fun changeEnableButton(name: Int, age: Int, city: Int): Boolean {
        return name == 1 && age == 1 && city == 1
    }

    suspend fun getDataCitiesByCodeCountry(country: String): List<ResponseCities> {
        return cityRepository.getDataCitiesByCodeCountry(country).sortedBy { myObject -> myObject.pais }
    }

    fun isCityInList(text: String, citiesList: ArrayList<ResponseCities>): Boolean{
        var cityFounded = false
        for(id in citiesList[0].data.indices){
            if (citiesList[0].data[id] == text){
                cityFounded = true
                break
            }
        }
        return cityFounded
    }

    fun setDataProfile(context: Context, userProfle: UserProfileData?): Int {
        if (sharedPreferences().set(context, KeySharedPreferences.NAME.value, userProfle!!.name) == true &&
        sharedPreferences().set(context, KeySharedPreferences.AGE.value, userProfle.age) == true &&
        sharedPreferences().set(context, KeySharedPreferences.CITY.value, userProfle.city) == true )
            return CodeResponseLoginUser.PERFIL_DERMATOLOGICO.code
        else
            return CodeResponseLoginUser.ERROR.code
    }
}