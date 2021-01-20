package com.tamizna.yukbisayuk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tamizna.yukbisayuk.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtNameApp.animate().setDuration(2_000).alpha(1F)
    }
}