package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.data.attributes.city.repository.CityRepository

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 1:35 p. m.
 * All rights reserved 2023.
 ****/

class UserProfileUseCase(cityRepository: CityRepository) {
    fun changeEnableButton(name: Int, age: Int, city: Int): Boolean {
        return name == 1 && age == 1 && city == 1
    }
}