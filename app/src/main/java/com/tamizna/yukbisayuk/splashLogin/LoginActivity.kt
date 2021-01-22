package com.tamizna.yukbisayuk.splashLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.databinding.ActivityLoginBinding
import com.tamizna.yukbisayuk.home.HomeActivity
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.roomDb.DatabaseBuilder
import com.tamizna.yukbisayuk.roomDb.DatabaseHelperImp
import com.tamizna.yukbisayuk.utils.DialogLoading
import com.tamizna.yukbisayuk.utils.ResourceUtil

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = LoginViewModel(DatabaseHelperImp(DatabaseBuilder.getInstance(this)))

        setupObserver()
        initListener()
    }

    private fun setupObserver() {
        val dialogLoading = DialogLoading(this)

        viewModel.login.observe(this) {
            when(it.state) {
                DataResult.State.LOADING -> {
                    dialogLoading.show()
                }
                DataResult.State.SUCCESS -> {
                    dialogLoading.dismiss()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                DataResult.State.ERROR -> {
                    dialogLoading.dismiss()
                    ResourceUtil.showCustomDialog(this, getString(R.string.ooops), it.errorMessage?:"", "ERROR")
                }
            }
        }

        viewModel.usernameValidation.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    binding.edtLayUsername.isErrorEnabled = false
                }
                DataResult.State.ERROR -> {
                    binding.edtLayUsername.error = it.errorMessage
                }
            }
        }

        viewModel.passwordValidation.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    binding.edtLayPassword.isErrorEnabled = false
                }
                DataResult.State.ERROR -> {
                    binding.edtLayPassword.error = it.errorMessage
                }
            }
        }
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.checkFormLogin(
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            )
        }
    }
}