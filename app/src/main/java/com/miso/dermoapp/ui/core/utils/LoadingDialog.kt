package com.miso.dermoapp.ui.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
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
    private var dialogSuccess: AlertDialog? = null

    @SuppressLint("MissingInflatedId")
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(context, R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.loading_dialog, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewLoadingDialog)
        val imageLoading = loadingDialogView.findViewById<ImageView>(R.id.imageViewLoading)
        animationLoading(imageLoading,true)
        textViewLoadingDialog.setText(text)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog!!.show()
    }

    private fun animationLoading(imageViewLoading: ImageView, state: Boolean) {
        val animation = if (state)
            R.anim.loading
        else
            R.anim.invisible
        imageViewLoading.startAnimation(AnimationUtils.loadAnimation(context, animation))
    }

    fun hideLoadingDialog(){
        dialog?.dismiss()
    }

    fun succesful() {
        hideLoadingDialog()
        val builder = AlertDialog.Builder(context, R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.dialog_success, null)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog!!.show()
    }
}