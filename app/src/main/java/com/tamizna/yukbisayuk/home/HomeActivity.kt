package com.tamizna.yukbisayuk.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.detailDonation.DetailDonationActivity
import com.tamizna.yukbisayuk.databinding.ActivityHomeBinding
import com.tamizna.yukbisayuk.historyDonation.HistoryActivity
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.utils.DialogLoading
import com.tamizna.yukbisayuk.utils.ResourceUtil

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
        val loadingDialog = DialogLoading(this)

        viewModel.donasi.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    loadingDialog.hide()
                    it.data?.run {
                        val dataFiltered = it.data.filter { item -> item.title.isNotEmpty() }
                        donationAdapter.updateList(dataFiltered)
                    }
                }
                DataResult.State.LOADING -> {
                    loadingDialog.show()
                }
                else -> {
                    loadingDialog.hide()
                    ResourceUtil.showCustomDialog(this, getString(R.string.ooops), it.errorMessage?:"", "ERROR")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> { super.onOptionsItemSelected(item) }
        }
    }
}