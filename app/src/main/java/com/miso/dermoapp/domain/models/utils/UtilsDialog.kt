package com.miso.dermoapp.domain.models.utils

import com.miso.dermoapp.R
import com.miso.dermoapp.domain.models.enumerations.CodeTypeSpinner

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.utils
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 6:07 a. m.
 * All rights reserved 2023.
 ****/

class UtilsDialog {
    fun getIdTitle(code: Int): Int {
        when (code) {
            CodeTypeSpinner.TYPE_KIN.code -> return R.string.seleccion_tipo_piel
            else -> return 0
        }
    }
}