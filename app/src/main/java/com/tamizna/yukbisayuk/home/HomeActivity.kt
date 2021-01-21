package com.tamizna.yukbisayuk.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.detailDonation.DetailDonationActivity
import com.tamizna.yukbisayuk.databinding.ActivityHomeBinding
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.utils.LoadingDialog

class HomeActivity : AppCompatActivity() {

     private lateinit var binding: ActivityHomeBinding
    private lateinit var donationAdapter: DonationAdapter

    private val viewModel: DonasiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

          binding = ActivityHomeBinding.inflate(layoutInflater)
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
        val loadingView = LoadingDialog.showLoading(this, resources.getString(R.string.loading))

        viewModel.donasi.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    loadingView.hide()
                    it.data?.run {
                        val dataFiltered = it.data.filter { item -> item.title.isNotEmpty() }
                        donationAdapter.updateList(dataFiltered)
                    }

                }
                DataResult.State.LOADING -> {
                    Log.d("DONASI", "LOADING")
                    loadingView.show()
                }
                else -> {
                    loadingView.hide()
                    Log.d("DONASI", "ERROR ${it.errorMessage}")
                }
            }
        }
    }
}