package com.miso.dermoapp.domain.models.enumerations


import android.content.Context
import android.content.res.Configuration
import com.miso.dermoapp.R
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 3/02/2023 at 8:01 p. m.
 * All rights reserved 2023.
 ****/

@Suppress("DEPRECATION")
enum class ResponseErrorField(private val labelId: Int){
    DEFAULT(R.string.espacio),
    ERROR_EMPTY(R.string.el_campo_esta_vacio),
    ERROR_INVALID_MAIL(R.string.ingrese_un_correo_valido),
    ERROR_LONG_CHARACTERS(R.string.debe_ser_mayor_a),
    ERROR_CHARACTERS(R.string.caracteres),
    ERROR_PASSWORD_DOESNT_MATCH(R.string.las_contrasenas_no_coinciden),
    ERROR_AGE_MAJOR_ZERO(R.string.laEdadDebeSerMayorACero);

    lateinit var label: String
        private set

    companion object {
        fun initialize(context: Context) {
            val config = Configuration()
            when (sharedPreferences().get(context,KeySharedPreferences.IDIOMA.value).toInt()) {
                1 -> {
                    config.locale = Locale("en")
                }
                2 -> {
                    config.locale = Locale("es")
                }
            }
            context.resources.updateConfiguration(config, null)
            for (value in ResponseErrorField.values()) value.label = context.getString(value.labelId)
        }
    }
}
