package com.tamizna.yukbisayuk.historyDonation

import androidx.recyclerview.widget.RecyclerView
import com.tamizna.yukbisayuk.databinding.ItemHistoryBinding
import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.utils.ResourceUtil

class HistoryViewHolder(val view: ItemHistoryBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(donationTransaction: DonationTransaction) {
        view.txtTitleDonation.text = donationTransaction.donationTitle
        view.txtDate.text = donationTransaction.date.toString()
        val amount = ResourceUtil.thousandSeparatorRupiah(donationTransaction.nominal.toString())
        view.txtNominal.text = "Rp $amount"
    }

}
