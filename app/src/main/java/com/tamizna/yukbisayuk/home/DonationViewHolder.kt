package com.tamizna.yukbisayuk.home

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.ItemDonasiBinding
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.utils.ResourceUtil
import kotlin.math.roundToInt

class DonationViewHolder(val binding: ItemDonasiBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemDonation: ResponseGetListDonasiItem) {
        binding.txtTitleDonation.text = itemDonation.title
        binding.txtInitiator.text = ResourceUtil.getString(R.string.yuk_bisa_yuk_team)
        val amount = ResourceUtil.thousandSeparatorRupiah(
            itemDonation.targetDonation.roundToInt().toString()
        )
        binding.txtCurrentDonation.text = "Rp $amount"
        binding.pbDonation.max = itemDonation.targetDonation.roundToInt()
        binding.pbDonation.progress = itemDonation.currentDonation.roundToInt()

        Glide.with(binding.imgDonation.context).load(itemDonation.photo).centerCrop()
            .error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_loading)
            .into(binding.imgDonation)
    }

}
