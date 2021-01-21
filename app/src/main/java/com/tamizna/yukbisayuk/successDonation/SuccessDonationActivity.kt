package com.tamizna.yukbisayuk.successDonation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.ActivitySuccessDonationBinding

class SuccessDonationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuccessDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}