package com.miso.dermoapp.ui.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.diagnosis.entitie.ResponseDiagnosis
import com.miso.dermoapp.domain.models.enumerations.CodeSharedPreferences
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import com.miso.dermoapp.ui.core.home.views.Welcome
import com.miso.dermoapp.ui.core.injury.views.Injuries


/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.utils
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 7:36 a. m.
 * All rights reserved 2023.
 ****/

class LoadingDialog (val context: Context, val text: String) {
    private var dialog: AlertDialog? = null
    private var dialogSuccess: AlertDialog? = null
    private var dialogWarning: AlertDialog? = null
    private var dialogError: AlertDialog? = null
    private var dialogCerrarSesion: AlertDialog? = null
    private var dialogCerrarTypeOfInjury: AlertDialog? = null
    private var dialogDataDiagnosis: AlertDialog?= null

    @SuppressLint("MissingInflatedId")
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(context, R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.loading_dialog, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewLoadingDialog)
        val imageLoading = loadingDialogView.findViewById<ImageView>(R.id.imageViewLoading)
        animationLoading(imageLoading,true)
        textViewLoadingDialog.setText(text)
        textViewLoadingDialog.gravity = Gravity.CENTER_HORIZONTAL
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog!!.show()
    }

    fun cerrarSesion(titulo: String, msg: String, code: Int) {
        val builder = AlertDialog.Builder(context,R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.cerrar_sesion, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewTittle)
        val textViewMessage = loadingDialogView.findViewById<TextView>(R.id.textViewMensaje)
        val buttonPositive = loadingDialogView.findViewById<Button>(R.id.buttonSi)
        val buttonNegative = loadingDialogView.findViewById<Button>(R.id.buttonNo)
        textViewLoadingDialog.setText(titulo)
        textViewMessage.setText(msg)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialogCerrarSesion = builder.create()
        dialogCerrarSesion!!.show()
        buttonNegative.setOnClickListener {
            dialogCerrarSesion?.dismiss()
        }
        buttonPositive.setOnClickListener {
            sharedPreferences().set(context, KeySharedPreferences.STATUS_PROFILE.value,code.toString())
            sharedPreferences().set(context, KeySharedPreferences.EMAIL.value, CodeSharedPreferences.DEFAULT.code)
            val intent= Intent(context, Welcome::class.java)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(
                R.anim.right_in, R.anim.right_out
            )
            context.finish()
        }
    }

    @SuppressLint("MissingInflatedId")
    fun mostrarDataDiagnostico(titulo: String, msg: ResponseDiagnosis) {
        val builder = AlertDialog.Builder(context,R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.mostrar_data_diagnostico, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewTittle)
        val textViewMessage = loadingDialogView.findViewById<TextView>(R.id.textViewMensaje)
        val buttonNegative = loadingDialogView.findViewById<Button>(R.id.buttonClose)
        textViewLoadingDialog.setText(titulo)
        if (msg.condition.isEmpty())
            textViewMessage.setText("Actualmente el sistema no tiene un diagnóstico generado para su lesión, por favor comuniquese con DermoApp para mayor información.")
        else
            textViewMessage.setText(msg.condition)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialogCerrarSesion = builder.create()
        dialogCerrarSesion!!.show()
        buttonNegative.setOnClickListener {
            dialogCerrarSesion?.dismiss()
        }
    }

    fun closeTypeOfInjury(titulo: String, msg: String, code: Int) {
        val builder = AlertDialog.Builder(context,R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.cerrar_sesion, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewTittle)
        val textViewMessage = loadingDialogView.findViewById<TextView>(R.id.textViewMensaje)
        val buttonPositive = loadingDialogView.findViewById<Button>(R.id.buttonSi)
        val buttonNegative = loadingDialogView.findViewById<Button>(R.id.buttonNo)
        textViewLoadingDialog.setText(titulo)
        textViewMessage.setText(msg)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialogCerrarSesion = builder.create()
        dialogCerrarSesion!!.show()
        buttonNegative.setOnClickListener {
            dialogCerrarSesion?.dismiss()
        }
        buttonPositive.setOnClickListener {
            val intent= Intent(context, Injuries::class.java)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(
                R.anim.right_in, R.anim.right_out
            )
            context.finish()
        }
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
        dialogSuccess?.dismiss()
        dialogWarning?.dismiss()
        dialogError?.dismiss()
        dialogCerrarSesion?.dismiss()
        dialogCerrarTypeOfInjury?.dismiss()
        dialogDataDiagnosis?.dismiss()
    }

    fun succesful(text: Int) {
        hideLoadingDialog()
        val builder = AlertDialog.Builder(context, R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.dialog_success, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewLoadingDialog)
        textViewLoadingDialog.setText(text)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialogSuccess = builder.create()
        dialogSuccess!!.show()
    }

    fun warning(text: String) {
        hideLoadingDialog()
        val builder = AlertDialog.Builder(context, R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.dialog_warning, null)
        val textViewLoadingDialog = loadingDialogView.findViewById<TextView>(R.id.textViewLoadingDialog)
        textViewLoadingDialog.setText(text)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialogWarning = builder.create()
        dialogWarning!!.show()
    }

    fun error() {
        hideLoadingDialog()
        val builder = AlertDialog.Builder(context, R.style.CustomDialog)
        val factory = LayoutInflater.from(context)
        val loadingDialogView : View = factory.inflate(R.layout.dialog_error, null)
        builder.setView(loadingDialogView)
        builder.setCancelable(false)
        dialogError = builder.create()
        dialogError!!.show()
    }
}