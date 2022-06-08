package com.nitviptv.hotelrecordform.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.nitviptv.hotelrecordform.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            var userName = binding.textUsername.text
            var password = binding.textPassword.text
        }


    }

    private fun testFunction(completionHandler: (myResponse: Boolean) -> Unit) {

        completionHandler.invoke(false)

    }


    private fun validateInputs(userName: String, password: String) {


    }


}