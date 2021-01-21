package com.tamizna.yukbisayuk.historyDonation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tamizna.yukbisayuk.databinding.ActivityHistoryBinding
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.roomDb.DatabaseBuilder
import com.tamizna.yukbisayuk.roomDb.DatabaseHelperImp

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
        viewModel.listTransactionDonation.observe(this, {
            when (it.state) {
                DataResult.State.LOADING -> {
                    Log.d("HISTORY", "LOADING")
                }
                DataResult.State.SUCCESS -> {
                    Log.d("HISTORY", "SUCCESS")
                    if (!it.data.isNullOrEmpty()) {
                        adapterHistory.updateList(it.data)
                    }
                }
                DataResult.State.ERROR -> {
                    Log.d("HISTORY", "ERROR ${it.errorMessage}")
                }
            }
        })
    }
}