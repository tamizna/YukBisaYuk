package com.tamizna.yukbisayuk

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.tamizna.yukbisayuk.databinding.ActivityDetailDonationBinding
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.viewModels.DetailDonationViewModel

class DetailDonationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDonationBinding
    private lateinit var donationId: String

    private lateinit var viewModel: DetailDonationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        donationId = intent.getStringExtra("id") ?: "0"

        viewModel = DetailDonationViewModel(donationId)


        binding = ActivityDetailDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        initListener()
    }

    private fun initListener() {
        binding.btnDonationNow.setOnClickListener {
            val bottomSheetInputNominal = InputNominalDonationBottomSheet.instance()
            supportFragmentManager.let {
                bottomSheetInputNominal.apply {
                    show(it, InputNominalDonationBottomSheet.TAG)
                }
            }
        }
    }

    private fun setupObservers() {
        Log.d("ID", "DONATION: $donationId")
        viewModel.detailDonation.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    it.data.let { item ->
                        binding.txtTitleDonation.text = item?.title
                        binding.txtCurrentTargetDonation.text =
                            "Rp ${item?.currentDonation} terkumpul dari Rp ${item?.targetDonation}"
                        binding.txtDescStory.text = HtmlCompat.fromHtml(
                            item?.description ?: "",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )

                        Glide.with(this).load(item?.photo).centerCrop()
                            .into(binding.imgDonation)
                    }
                }
                DataResult.State.LOADING -> {
                    Log.d("DONASI", "DETAIL LOADING")
                }
                else -> {
                    Log.d("DONASI", "DETAIL ERROR ${it.errorMessage}")
                }
            }
        }
    }
}