package com.tamizna.yukbisayuk.inputNominal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.BottomSheetInputNominalDonationBinding
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.roomDb.DatabaseBuilder
import com.tamizna.yukbisayuk.roomDb.DatabaseHelperImp
import com.tamizna.yukbisayuk.successDonation.SuccessDonationActivity
import java.util.*

class InputNominalDonationBottomSheet : BottomSheetDialogFragment() {

    private lateinit var donationId: String
    private lateinit var donationTitle: String
    private lateinit var binding: BottomSheetInputNominalDonationBinding
    private lateinit var viewModel: InputNominalViewModel

    companion object {
        fun instance(donationId: String, donationTitle: String): InputNominalDonationBottomSheet {
            val args = Bundle()
            args.putSerializable("donationId", donationId)
            args.putSerializable("donationTitle", donationTitle)
            val f = InputNominalDonationBottomSheet()
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetInputNominalDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            if (it.getSerializable("donationId") != null) {
                donationId = it.getSerializable("donationId") as String
                donationTitle = it.getSerializable("donationTitle") as String
            }
        }

        viewModel = InputNominalViewModel(DatabaseHelperImp(DatabaseBuilder.getInstance(context!!)))
        initListener()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.dataDonation.observe(viewLifecycleOwner, {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    Toast.makeText(context, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    this.dismiss()
                    startActivity(Intent(activity, SuccessDonationActivity::class.java))
                }
                DataResult.State.LOADING -> {
                    Log.d("SAVE_DONASI", "LOADING")
                }
                DataResult.State.ERROR -> {
                    Log.d("SAVE_DONASI", "ERROR ${it.errorMessage}")
                }
            }
        })

        viewModel.nominalValidation.observe(viewLifecycleOwner, {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    viewModel.saveDonation(
                        DonationTransaction(
                            0,
                            donationId,
                            donationTitle,
                            binding.edtNominalDonation.text.toString().toInt(),
                            Calendar.getInstance().time
                        )
                    )
                }
                DataResult.State.ERROR -> {
                    binding.edtNominalDonation.error = it.errorMessage
                }
            }
        })
    }

    private fun initListener() {
        binding.btnDonation.setOnClickListener {
            viewModel.checkInputNominal(binding.edtNominalDonation.text.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }
}