package com.miso.dermoapp.domain.models.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.miso.dermoapp.domain.models.enumerations.CodeSharedPreferences


/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.utils
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 9:51 p. m.
 * All rights reserved 2023.
 ****/

class sharedPreferences {

    fun get(context: Context,dato: String):String{
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("SESION", MODE_PRIVATE)
        return sharedPreferences.getString(dato, CodeSharedPreferences.DEFAULT.code).toString()
    }

    fun set(context: Context, clave: String, valor: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("SESION", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(clave, valor)
            editor.apply()
            true
        } catch (e: Exception) {
            false
        }
    }

}