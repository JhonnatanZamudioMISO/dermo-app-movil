package com.miso.dermoapp.domain.useCases

import android.content.Context
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 12/03/2023 at 7:49 p. m.
 * All rights reserved 2023.
 ****/

class DistributionUseCase {
    fun saveDistribution(context: Context, value: String) {
        sharedPreferences().set(context, KeySharedPreferences.DISTRIBUTION.value,value)
    }
}