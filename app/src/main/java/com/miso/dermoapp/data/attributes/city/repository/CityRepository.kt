package com.miso.dermoapp.data.attributes.city.repository

import com.miso.dermoapp.data.attributes.city.datasource.CityDataSourceLocal
import com.miso.dermoapp.data.attributes.city.entitie.ResponseCities

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.city.repository
 * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 12:55 p. m.
 * All rights reserved 2023.
 ****/

class CityRepository (private val citiesDataSourceLocal: CityDataSourceLocal) : CityRepositoryInterface {
    companion object{
        @Volatile
        private var instance: CityRepository? = null
        fun getInstance(
            citiesDataSourceLocal: CityDataSourceLocal
        ): CityRepository =
            instance ?: synchronized(this) {
                instance ?: CityRepository(citiesDataSourceLocal)
            }
    }

    override suspend fun getDataCitiesByCodeCountry(country: String):List<ResponseCities>{
        return citiesDataSourceLocal.getDataCitiesByCodeCountry(country)
    }
}
