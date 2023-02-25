package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.city.entitie.ResponseCities
import com.miso.dermoapp.data.attributes.city.repository.CityRepository

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
}