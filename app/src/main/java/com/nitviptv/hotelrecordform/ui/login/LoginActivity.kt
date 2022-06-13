package com.nitviptv.hotelrecordform.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nitviptv.hotelrecordform.data.ApiInterface
import com.nitviptv.hotelrecordform.data.repository.LoginRepo
import com.nitviptv.hotelrecordform.databinding.ActivityLoginBinding
import com.nitviptv.hotelrecordform.ui.form.FormActivity
import com.nitviptv.hotelrecordform.utils.toast

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loginRepo = LoginRepo(ApiInterface.getRetrofit())

        viewModel =
            ViewModelProvider(this, LoginViewModelFactory(loginRepo))[LoginViewModel::class.java]

        viewModel.loginResult.observe(this, Observer { loginResult ->
            Log.d("Sheershak", "$loginResult")
            if (loginResult.ID != 0) {
                startActivity(Intent(this, FormActivity::class.java))
                finish()
            } else toast("Login Details Incorrect.")
        })


        binding.btnLogin.setOnClickListener {
            var username = binding.textUsername.text.toString()
            var password = binding.textPassword.text.toString()
            if (validateInputs(username, password)) {
                viewModel.doLogin(username, password)
            }
        }


    }

    private fun validateInputs(userName: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(userName.trim()) -> {
                binding.layoutUsername.error = "Please Enter Username"
                false

            }
            TextUtils.isEmpty(password.trim()) -> {
                binding.layoutPassword.error = "Please Enter Password"
                false
            }
            else -> true
        }
    }

    class LoginViewModelFactory(private val loginRepo: LoginRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(loginRepo) as T
        }
    }


}