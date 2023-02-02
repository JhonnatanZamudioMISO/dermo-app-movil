package com.miso.dermoapp.ui.core.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.miso.dermoapp.R
import com.miso.dermoapp.domain.models.enumerations.TypeSnackBar

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.utils
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 9:49 a. m.
 * All rights reserved 2023.
 ****/

class CustomSnackBar {
    fun showSnackBar(message: String, layoutContain: ConstraintLayout, type: Int, context: Context) {
        when(type){
            TypeSnackBar.CLOSE_APP.code -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    buildSnackBar(layoutContain, message, context.getString(R.string.cerrar), true,
                        context.getColor(R.color.error))
                else
                    Color.RED
            }
            TypeSnackBar.ERROR.code -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    buildSnackBar(layoutContain, message, context.getString(R.string.cerrar), false,
                        context.getColor(R.color.error))
                else
                    Color.RED
            }
            TypeSnackBar.INFO.code -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    buildSnackBar(layoutContain, message, context.getString(R.string.cerrar), false,
                        context.getColor(R.color.info))
                else
                    Color.RED
            }
            TypeSnackBar.WARNING.code -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    buildSnackBar(layoutContain, message, context.getString(R.string.cerrar), false,
                        context.getColor(R.color.warning))
                else
                    Color.RED
            }
        }
    }

    private fun buildSnackBar(layoutContain: ConstraintLayout, message: String, messageAction: String,
                              exit: Boolean, rgb: Int) {
        val mySnackbar = Snackbar.make(layoutContain, message, Snackbar.LENGTH_INDEFINITE)
        mySnackbar.setAction(messageAction, {
            if (exit)
                System.exit(0)
        })
        mySnackbar.setBackgroundTint(rgb)
        mySnackbar.show()
    }

}