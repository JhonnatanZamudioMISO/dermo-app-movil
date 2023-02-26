package com.miso.dermoapp.data.attributes.city.source

import android.content.Context
import com.google.gson.Gson
import com.miso.dermoapp.data.attributes.city.entitie.ResponseCities
import com.miso.dermoapp.data.json.JSONConverter
import org.json.JSONArray
import org.json.JSONObject

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.city.source
 * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 12:42 p. m.
 * All rights reserved 2023.
 ****/

class CityJSON(private val context: Context){
    val path: String = "json_cities.txt"
    lateinit var jsonConverter : JSONConverter
    lateinit var jsonArray : JSONArray

    fun getDataCitiesByCodeCountry(country: String): List<ResponseCities> {
        val citiesList: ArrayList<ResponseCities> = ArrayList()
        jsonConverter = JSONConverter()
        jsonArray = jsonConverter.getData(path,context)
        var data: JSONObject
        for (id in 0 until jsonArray.length()) {
            data = jsonArray.getJSONObject(id)
            if (data["pais"]==country) {
                citiesList.add(Gson().fromJson(data.toString(), ResponseCities::class.java))
            }
        }
        return citiesList
    }

}