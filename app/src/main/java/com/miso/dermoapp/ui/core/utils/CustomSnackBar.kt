package com.miso.dermoapp.ui.core.utils

import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.utils
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 9:49 a. m.
 * All rights reserved 2023.
 ****/

class CustomSnackBar {

    fun showSnackBar(message: String, layoutContain: ConstraintLayout) {
        val mySnackbar = Snackbar.make(layoutContain, message, Snackbar.LENGTH_INDEFINITE)
        mySnackbar.setAction("CERRAR", {
            System.exit(0)
        })
        mySnackbar.setBackgroundTint(Color.rgb(148, 65, 69))
        mySnackbar.show()
    }

}