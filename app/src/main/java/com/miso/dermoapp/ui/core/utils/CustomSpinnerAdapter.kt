package com.miso.dermoapp.ui.core.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miso.dermoapp.R
import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType
import com.miso.dermoapp.domain.models.enumerations.CodeTypeSpinner

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.utils
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:49 a. m.
 * All rights reserved 2023.
 ****/

class CustomSpinnerAdapter(private val data: List<Any>, private val code: Int) :
    RecyclerView.Adapter<CustomSpinnerAdapter.CustomViewHolder>() {

    var customActionsSpinner: CustomActionSpinner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.spinner_items, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val data = data[position]
        createStyleDialog(position, holder)
        setDataSpinner(data, holder)
        holder.container.setOnClickListener {
            customActionsSpinner?.onItemSelected(position)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataSpinner(data: Any, holder: CustomViewHolder) {
        when (code) {
            CodeTypeSpinner.TYPE_KIN.code -> {
                data as ResponseKinType
                holder.description.text = data.abbreviate + " - " + data.description
            }
        }
    }

    private fun createStyleDialog(position: Int, holder: CustomViewHolder) {
        if (position % 2 == 0) {
            holder.container.setBackgroundResource(R.drawable.background_item_1)
            holder.description.setTextColor(-0x1)
        } else
            holder.container.setBackgroundResource(R.drawable.background_item_2)
    }

    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imageViewIcon)
        val description: TextView = view.findViewById(R.id.textViewDescription)
        val container: LinearLayout = view.findViewById(R.id.linearLayoutContainer)
    }

    interface CustomActionSpinner {
        fun onItemSelected(position: Int)
    }

}
