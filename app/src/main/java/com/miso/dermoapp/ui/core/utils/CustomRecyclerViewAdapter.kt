package com.miso.dermoapp.ui.core.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miso.dermoapp.data.attributes.injury.entitie.Injuries
import com.miso.dermoapp.databinding.AdapterInjuryBinding

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.ui.core.utils
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:49 a. m.
 * All rights reserved 2023.
 ****/
class CustomRecyclerViewAdapter: RecyclerView.Adapter<MainViewHolder>() {
    var injuries = mutableListOf<Injuries>()

    @SuppressLint("NotifyDataSetChanged")
    fun setInjuryList(injuries: List<Injuries>) {

        this.injuries = injuries.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(AdapterInjuryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            bind(injuries[position])
        }

    }

    override fun getItemCount(): Int {
        return injuries.size
    }
}
class MainViewHolder(val binding: AdapterInjuryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(injuries: Injuries) {
        binding.p= injuries
    }


}
