package com.tamizna.yukbisayuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tamizna.yukbisayuk.adapters.DonationAdapter
import com.tamizna.yukbisayuk.databinding.ActivityMainBinding
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.viewModels.DonasiViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var donationAdapter: DonationAdapter

    private val viewModel: DonasiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onItemClick = { donation: ResponseGetListDonasiItem ->
            val intent = Intent(this, DetailDonationActivity::class.java)
            intent.putExtra("id", donation.id)
            startActivity(intent)
        }

        donationAdapter = DonationAdapter(onItemClick)
        binding.rvDonation.run {
            layoutManager = LinearLayoutManager(context)
            adapter = donationAdapter
        }

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.donasi.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    it.data?.run {
                        val dataFiltered = it.data.filter { item -> item.title.isNotEmpty() }
                        donationAdapter.updateList(dataFiltered)
                    }

                }
                DataResult.State.LOADING -> {
                    Log.d("DONASI", "LOADING")
                }
                else -> {
                    Log.d("DONASI", "ERROR ${it.errorMessage}")
                }
            }
        }
    }
}