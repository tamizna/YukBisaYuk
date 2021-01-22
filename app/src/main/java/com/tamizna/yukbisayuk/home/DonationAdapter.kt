package com.tamizna.yukbisayuk.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamizna.yukbisayuk.databinding.ItemDonasiBinding
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem

class DonationAdapter(private val onItemClick : (ResponseGetListDonasiItem) -> Unit) : RecyclerView.Adapter<DonationViewHolder>() {

    private val donations : MutableList<ResponseGetListDonasiItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDonasiBinding.inflate(inflater, parent, false)
        return DonationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        holder.bind(donations[position])

        Log.d("CREATED_AT", "${donations[position].createdAt}")

        holder.binding.root.setOnClickListener {
            onItemClick(donations[position])
        }
    }

    override fun getItemCount(): Int = donations.size

    fun updateList(list : List<ResponseGetListDonasiItem>) {
        donations.clear()
        donations.addAll(list)
        notifyDataSetChanged()
    }
}