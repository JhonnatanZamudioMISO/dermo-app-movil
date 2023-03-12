package com.miso.dermoapp.domain.useCases

import android.content.Context
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 6:32 p. m.
 * All rights reserved 2023.
 ****/

class FormOfTheInjuryUseCase {

    fun saveFormOfTheInjury(context: Context, value: String) {
        sharedPreferences().set(context, KeySharedPreferences.FORM_OF_THE_INJURY.value,value)
    }
}