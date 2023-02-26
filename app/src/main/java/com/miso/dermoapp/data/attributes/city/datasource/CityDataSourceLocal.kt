package com.miso.dermoapp.data.attributes.city.datasource

import com.miso.dermoapp.data.attributes.city.entitie.ResponseCities
import com.miso.dermoapp.data.attributes.city.source.CityJSON

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.city.datasource
 * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 12:56 p. m.
 * All rights reserved 2023.
 ****/

class CityDataSourceLocal (private val cityJSON: CityJSON){
    companion object {
        private var INSTANCE: CityDataSourceLocal? = null
        fun getInstance(cityJSON: CityJSON): CityDataSourceLocal =
            INSTANCE ?: CityDataSourceLocal(cityJSON)
    }

    fun getDataCitiesByCodeCountry(country: String): List<ResponseCities> {
        return cityJSON.getDataCitiesByCodeCountry(country)
    }
}