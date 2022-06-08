package com.nitviptv.hotelrecordform.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nitviptv.hotelrecordform.R
import com.nitviptv.hotelrecordform.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}