package com.tamizna.yukbisayuk.historyDonation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamizna.yukbisayuk.databinding.ItemHistoryBinding
import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {

    private val donations: MutableList<DonationTransaction> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(donations[position])
    }

    override fun getItemCount(): Int = donations.size

    fun updateList(list : List<DonationTransaction>) {
        donations.clear()
        donations.addAll(list)
        notifyDataSetChanged()
    }
}