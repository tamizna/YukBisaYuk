package com.tamizna.yukbisayuk.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.ActivityHomeBinding
import com.tamizna.yukbisayuk.detailDonation.DetailDonationActivity
import com.tamizna.yukbisayuk.historyDonation.HistoryActivity
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.splashLogin.LoginActivity
import com.tamizna.yukbisayuk.utils.DialogLoading
import com.tamizna.yukbisayuk.utils.ResourceUtil

class HomeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var donationAdapter: DonationAdapter
    private var dataFiltered: List<ResponseGetListDonasiItem> = mutableListOf()

    private val viewModel: HomeViewModel by viewModels()

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

        ArrayAdapter.createFromResource(
            this,
            R.array.donation_category,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDonationCategory.adapter = adapter
        }

        binding.spinnerDonationCategory.onItemSelectedListener = this
    }

    private fun setupObserver() {
        val loadingDialog = DialogLoading(this)

        viewModel.donasi.observe(this) { it ->
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    loadingDialog.hide()
                    it.data?.run {
                        dataFiltered = it.data.filter { item -> item.title.isNotEmpty() }

                        if (!it.data.isNullOrEmpty()) {
                            donationAdapter.updateList(dataFiltered)
                        } else {
                            binding.txtLabel.visibility = View.GONE
                            binding.spinnerDonationCategory.visibility = View.GONE
                            binding.emptyView.visibility = View.VISIBLE
                        }
                    }
                }
                DataResult.State.LOADING -> {
                    loadingDialog.show()
                }
                else -> {
                    loadingDialog.hide()
                    binding.emptyView.visibility = View.VISIBLE
                    binding.txtLabel.visibility = View.GONE
                    binding.spinnerDonationCategory.visibility = View.GONE
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.action_logout -> {
                val dialogLoading = DialogLoading(this)

                viewModel.loggingOut.observe(this) {
                    when (it.state) {
                        DataResult.State.SUCCESS -> {
                            dialogLoading.dismiss()
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                        DataResult.State.LOADING -> {
                            dialogLoading.show()
                        }
                    }
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val position = parent.getItemAtPosition(pos)
        donationAdapter.updateList(viewModel.filteredData(dataFiltered, position))
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

}