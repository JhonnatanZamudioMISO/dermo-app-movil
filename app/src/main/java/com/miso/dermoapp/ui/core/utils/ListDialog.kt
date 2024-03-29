package com.miso.dermoapp.ui.core.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.DialogListBinding
import com.miso.dermoapp.domain.models.utils.UtilsDialog

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.utils
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:48 a. m.
 * All rights reserved 2023.
 ****/

class ListDialog(
    private val data: List<Any>,
    private val code: Int,
    private var customActionSpinner: CustomSpinnerAdapter.CustomActionSpinner
): DialogFragment() {
    private var mBinding: DialogListBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("UseGetLayoutInflater")
    @Suppress("UNCHECKED_CAST")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_list, null, false)
        val alertDialog = AlertDialog.Builder(activity,R.style.CustomDialogTheme)
        alertDialog.setView(mBinding?.root)
        alertDialog.setTitle(UtilsDialog().getIdTitle(code))
        val dialog = alertDialog.create()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val adapter = CustomSpinnerAdapter(data,code)
        val layoutManager = LinearLayoutManager(context)
        mBinding?.rvItems?.layoutManager = layoutManager
        mBinding?.rvItems?.adapter = adapter
        adapter.customActionsSpinner = object : CustomSpinnerAdapter.CustomActionSpinner {
            override fun onItemSelected(position: Int) {
                customActionSpinner.onItemSelected(position)
                dismiss()
            }
        }
        return dialog
    }

}