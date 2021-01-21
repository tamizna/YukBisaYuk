package com.tamizna.yukbisayuk.successDonation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.ActivitySuccessDonationBinding
import com.tamizna.yukbisayuk.historyDonation.HistoryActivity
import com.tamizna.yukbisayuk.home.HomeActivity

class SuccessDonationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuccessDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.btnGoHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.btnSeeHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }
    }
}