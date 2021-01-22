package com.tamizna.yukbisayuk.historyDonation

import androidx.recyclerview.widget.RecyclerView
import com.tamizna.yukbisayuk.databinding.ItemHistoryBinding
import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.utils.ResourceUtil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewHolder(val view: ItemHistoryBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(donationTransaction: DonationTransaction) {
        view.txtTitleDonation.text = donationTransaction.donationTitle

        val outputFormatter : DateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy")
        val output = outputFormatter.format(donationTransaction.date)
        view.txtDate.text = output
        val amount = ResourceUtil.thousandSeparatorRupiah(donationTransaction.nominal.toString())
        view.txtNominal.text = "Rp $amount"
    }

}
