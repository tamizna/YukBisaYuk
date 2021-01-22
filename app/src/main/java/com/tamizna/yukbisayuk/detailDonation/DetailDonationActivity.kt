package com.tamizna.yukbisayuk.detailDonation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.ActivityDetailDonationBinding
import com.tamizna.yukbisayuk.inputNominal.InputNominalDonationBottomSheet
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.utils.DialogLoading
import com.tamizna.yukbisayuk.utils.ResourceUtil
import kotlin.math.roundToInt

class DetailDonationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDonationBinding
    private lateinit var donationId: String
    private lateinit var donationTitle: String

    private lateinit var viewModel: DetailDonationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        donationId = intent.getStringExtra("id") ?: "0"

        viewModel = DetailDonationViewModel(donationId)

        binding = ActivityDetailDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_donasi)

        setupObservers()
        initListener()
    }

    private fun initListener() {
        binding.btnDonationNow.setOnClickListener {
            val bottomSheetInputNominal =
                InputNominalDonationBottomSheet.instance(donationId, donationTitle)
            bottomSheetInputNominal.show(supportFragmentManager, "bottom_sheet")
        }
    }

    private fun setupObservers() {
        val dialogLoading = DialogLoading(this)

        viewModel.detailDonation.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    dialogLoading.dismiss()
                    it.data.let { item ->
                        donationTitle = item?.title ?: ""
                        val targetAmount = ResourceUtil.thousandSeparatorRupiah(
                            item?.targetDonation?.roundToInt().toString()
                        )
                        val currentAmount = ResourceUtil.thousandSeparatorRupiah(
                            item?.currentDonation?.roundToInt().toString()
                        )
                        binding.txtTitleDonation.text = item?.title
                        binding.txtCurrentTargetDonation.text =
                            "Rp $currentAmount terkumpul dari Rp $targetAmount"
                        binding.txtDescStory.text = HtmlCompat.fromHtml(
                            item?.description ?: "",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )

                        Glide.with(this).load(item?.photo).centerCrop()
                            .error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_loading)
                            .into(binding.imgDonation)

                        binding.pbTargetDonation.max = item?.targetDonation?.toInt() ?: 0
                        binding.pbTargetDonation.progress = item?.currentDonation?.toInt() ?: 0
                    }
                }
                DataResult.State.LOADING -> {
                    dialogLoading.show()
                }
                else -> {
                    dialogLoading.dismiss()
                    ResourceUtil.showCustomDialog(
                        this,
                        getString(R.string.ooops),
                        it.errorMessage ?: "",
                        "ERROR"
                    )
                }
            }
        }
    }
}