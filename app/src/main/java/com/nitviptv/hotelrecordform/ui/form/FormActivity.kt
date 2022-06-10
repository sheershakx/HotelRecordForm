package com.nitviptv.hotelrecordform.ui.form

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nitviptv.hotelrecordform.databinding.ActivityFormBinding
import com.nitviptv.hotelrecordform.utils.transformIntoDatePicker
import com.nitviptv.hotelrecordform.utils.transformIntoTimePicker
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    lateinit var binding: ActivityFormBinding
    lateinit var viewModel: FormViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FormViewModel::class.java]

        binding.textCheckinDate.transformIntoDatePicker(this, "yyyy/MM/dd")
        binding.textCheckinTime.transformIntoTimePicker(this, "hh:mm")
        binding.textCheckoutDate.transformIntoDatePicker(this, "yyyy/MM/dd")
        binding.textCheckoutTime.transformIntoTimePicker(this, "hh:mm")

        binding.btnSave.setOnClickListener {
            //call viewmodel function
            viewModel.saveEntryForm()
        }


    }
}