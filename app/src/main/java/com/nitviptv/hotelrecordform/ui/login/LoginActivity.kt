package com.nitviptv.hotelrecordform.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.nitviptv.hotelrecordform.databinding.ActivityLoginBinding
import kotlinx.coroutines.delay

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        viewModel.loginResult.observe(this, Observer { loginResult ->
            //do login or show error
            when (loginResult) {
                true -> Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT)
                    .show()
                else -> Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })


        binding.btnLogin.setOnClickListener {
            var userName = binding.textUsername.text.toString()
            var password = binding.textPassword.text.toString()
            if (validateInputs(userName, password)) {
                viewModel.doLogin(userName, password)
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


}