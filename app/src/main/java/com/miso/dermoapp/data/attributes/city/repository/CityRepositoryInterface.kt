package com.miso.dermoapp.data.attributes.city.repository

import com.miso.dermoapp.data.attributes.city.entitie.ResponseCities

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.city.repository
 * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 12:54 p. m.
 * All rights reserved 2023.
 ****/

interface CityRepositoryInterface {
    suspend fun getDataCitiesByCodeCountry(country: String) : List<ResponseCities>
}