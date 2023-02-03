package com.miso.dermoapp.domain.useCases

import android.content.Context
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 11:55 p. m.
 * All rights reserved 2023.
 ****/

class SelectLanguageUseCase {
    fun saveDefaultLanguage(context: Context, value: String) {
        sharedPreferences().set(context,KeySharedPreferences.IDIOMA.value,value)
    }
}