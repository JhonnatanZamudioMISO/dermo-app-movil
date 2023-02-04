package com.miso.dermoapp.ui.core.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.miso.dermoapp.R

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.utils
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 7:36 a. m.
 * All rights reserved 2023.
 ****/

class LoadingDialog (val context: Context, val text: String) {
    private var dialog: AlertDialog? = null

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(context, R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.loading_dialog, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewLoadingDialog)
        textViewLoadingDialog.setText(text)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog!!.show()
    }

    fun hideLoadingDialog(){
        dialog?.dismiss()
    }
}