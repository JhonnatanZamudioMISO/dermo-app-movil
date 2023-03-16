package com.miso.dermoapp.data.json

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.json
 * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 12:49 p. m.
 * All rights reserved 2023.
 ****/

class JSONConverter {
    var jsonArray : JSONArray = JSONArray()
    fun getData(path: String, context: Context) : JSONArray {
        val jsonString = getDataTXT(path, context)
        if (jsonString != null) {
            try {
                jsonArray = JSONArray(JSONObject(jsonString).optString("data"))
            } catch (ignore: Exception) {
            }
        }
        return jsonArray
    }

    fun getDataTXT(path: String,context: Context): String? {
        var aux: String? = null
        try {
            val bufferedReader = BufferedReader(InputStreamReader(context.assets.open(path)))
            aux = bufferedReader.readLine()
            bufferedReader.close()
        } catch (ignore: Exception) { }
        return aux
    }

}