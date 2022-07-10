package com.nitviptv.hotelrecordform.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nitviptv.hotelrecordform.R
import com.nitviptv.hotelrecordform.databinding.ActivityDashboardBinding
import com.nitviptv.hotelrecordform.ui.form.FormActivity
import com.nitviptv.hotelrecordform.utils.toast

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardGuestForm.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
        binding.cardGuestReport.setOnClickListener {
            toast("Function is in development process.")
        }
    }
}