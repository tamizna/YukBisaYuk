package com.tamizna.yukbisayuk.historyDonation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.ActivityHistoryBinding
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.roomDb.DatabaseBuilder
import com.tamizna.yukbisayuk.roomDb.DatabaseHelperImp
import com.tamizna.yukbisayuk.utils.DialogLoading
import com.tamizna.yukbisayuk.utils.ResourceUtil

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapterHistory: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Riwayat Donasi"

        adapterHistory = HistoryAdapter()
        binding.rvHistory.run {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = adapterHistory
        }

        viewModel = HistoryViewModel(DatabaseHelperImp(DatabaseBuilder.getInstance(this)))
        setupObservers()
        viewModel.getListTransaction()
    }

    private fun setupObservers() {
        val dialogLoading = DialogLoading(this)

        viewModel.listTransactionDonation.observe(this, {
            when (it.state) {
                DataResult.State.LOADING -> {
                    dialogLoading.show()
                }
                DataResult.State.SUCCESS -> {
                    dialogLoading.dismiss()
                    if (!it.data.isNullOrEmpty()) {
                        adapterHistory.updateList(it.data)
                    } else {
                        binding.emptyView.visibility = View.VISIBLE
                    }
                }
                DataResult.State.ERROR -> {
                    dialogLoading.dismiss()
                    binding.emptyView.visibility = View.VISIBLE
                    ResourceUtil.showCustomDialog(this, getString(R.string.ooops), it.errorMessage?:"", "ERROR")
                }
            }
        })
    }
}