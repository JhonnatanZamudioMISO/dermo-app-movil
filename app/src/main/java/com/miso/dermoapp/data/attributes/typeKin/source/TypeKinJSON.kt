package com.miso.dermoapp.data.attributes.typeKin.source

import android.content.Context
import com.google.gson.Gson
import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType
import com.miso.dermoapp.data.json.JSONConverter
import org.json.JSONArray
import org.json.JSONObject

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.typeKin.source
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:10 a. m.
 * All rights reserved 2023.
 ****/

class TypeKinJSON(private val context: Context){
    val path: String = "json_type_kin.txt"
    lateinit var jsonConverter : JSONConverter
    lateinit var jsonArray : JSONArray

    fun getDataTypeDocument(): List<ResponseKinType> {
        val documentTypeList: ArrayList<ResponseKinType> = ArrayList()
        jsonConverter = JSONConverter()
        jsonArray = jsonConverter.getData(path,context)
        var data: JSONObject
        for (id in 0 until jsonArray.length()) {
            data = jsonArray.getJSONObject(id)
            documentTypeList.add(Gson().fromJson(data.toString(), ResponseKinType::class.java))
        }
        return documentTypeList
    }
}