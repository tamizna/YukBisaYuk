package com.tamizna.yukbisayuk.splashLogin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.yukbisayuk.databinding.ActivitySplashBinding
import com.tamizna.yukbisayuk.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtNameApp.animate().setDuration(2_000).alpha(1F)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.pageStart.observe(this) {
                if (it == Page.Home) {
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                finish()
            }
        }, 3000)
    }
}