package com.nitviptv.hotelrecordform.ui.form

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nitviptv.hotelrecordform.R
import com.nitviptv.hotelrecordform.data.ApiInterface
import com.nitviptv.hotelrecordform.data.repository.FormRepo
import com.nitviptv.hotelrecordform.data.repository.LoginRepo
import com.nitviptv.hotelrecordform.databinding.ActivityFormBinding
import com.nitviptv.hotelrecordform.domain.form.FormData
import com.nitviptv.hotelrecordform.ui.login.LoginViewModel
import com.nitviptv.hotelrecordform.utils.toast
import com.nitviptv.hotelrecordform.utils.transformIntoDatePicker
import com.nitviptv.hotelrecordform.utils.transformIntoTimePicker

class FormActivity : AppCompatActivity() {
    lateinit var binding: ActivityFormBinding
    lateinit var viewModel: FormViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val formRepo = FormRepo(ApiInterface.getRetrofit())

        viewModel =
            ViewModelProvider(this, FormViewModelFactory(formRepo))[FormViewModel::class.java]

        viewModel.formResponse.observe(this, Observer { formResponse ->
            if (formResponse == -1) {
                toast("Form Saved !!")
            } else toast("Error in saving form !!")
        })

        setDropdownAdapter()

        binding.textCheckinDate.transformIntoDatePicker(this, "yyyy/MM/dd")
        binding.textCheckinTime.transformIntoTimePicker(this, "hh:mm")
        binding.textCheckoutDate.transformIntoDatePicker(this, "yyyy/MM/dd")
        binding.textCheckoutTime.transformIntoTimePicker(this, "hh:mm")

        binding.btnPhotoId.setOnClickListener {

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, 1001)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }

        }
        binding.btnSave.setOnClickListener {
            var firstName = binding.textFirstName.text.toString()
            var lastName = binding.textLastName.text.toString()
            var province = binding.textProvince.text.toString()
            var city = binding.textCity.text.toString()
            var address = binding.textAddress.text.toString()
            var email = binding.textEmail.text.toString()
            var mobile = binding.textMobile.text.toString()
            var checkInDate = binding.textCheckinDate.text.toString()
            var checkInTime = binding.textCheckinTime.text.toString()
            var checkOutDate = binding.textCheckoutDate.text.toString()
            var checkOutTime = binding.textCheckoutTime.text.toString()
            var noOfPerson = binding.textNosGuest.text.toString()
            var roomNo = binding.textRoomNo.text.toString()
            var imagePath = "image path"

            if (validateInputs(
                    firstName,
                    address,
                    mobile,
                    checkInDate,
                    checkInTime,
                    checkOutDate,
                    noOfPerson,
                    roomNo,
                    imagePath
                )
            ) {

                var formData = FormData(
                    Address = address,
                    CheckInDate = checkInDate,
                    CheckInTime = checkInTime,
                    CheckOutDate = checkOutDate,
                    CheckOutTime = checkOutTime,
                    City = city,
                    Email = email,
                    FirstName = firstName,
                    ImagePath = imagePath,
                    LastName = lastName,
                    Mobile = mobile,
                    NoOfPerson = noOfPerson.toInt(),
                    Province = province,
                    RoomNo = roomNo,
                    Vendor = 1
                )

                viewModel.saveForm(formData)


            }

        }
    }

    private fun setDropdownAdapter() {
        val cityList = listOf<String>("Itahari", "Dharan", "Biratnagar", "Damak", "Bharatpur")
        val cityAdapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            cityList
        )
        binding.textCity.setAdapter(cityAdapter)

        val provinceList = listOf<String>(
            "Province 1",
            "Madesh Pradesh",
            "Bagmati Pradesh",
            "Gandaki Pradesh",
            "Lumbini Pradesh",
            "Karnali Pradesh",
            "Sudurpaschim Pradesh"
        )
        val provinceAdapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            provinceList
        )
        binding.textProvince.setAdapter(provinceAdapter)
    }

    private fun validateInputs(
        firstName: String,
        address: String,
        mobile: String,
        checkInDate: String,
        checkInTime: String,
        checkOutDate: String,
        noOfPerson: String,
        roomNo: String,
        imagePath: String
    ): Boolean {

        return when {
            TextUtils.isEmpty(firstName.trim()) -> {
                binding.layoutFirstName.error = "Please Enter Name"
                return false
            }
            TextUtils.isEmpty(address.trim()) -> {
                binding.layoutAddress.error = "Please Enter Address"
                return false
            }
            TextUtils.isEmpty(mobile.trim()) -> {
                binding.layoutMobile.error = "Please Enter Mobile"
                return false
            }
            TextUtils.isEmpty(checkInDate.trim()) -> {
                binding.layoutCheckinDate.error = "Please Enter Check-in date"
                return false
            }
            TextUtils.isEmpty(checkInTime.trim()) -> {
                binding.layoutCheckinTime.error = "Please Enter Check-in time"
                return false
            }
            TextUtils.isEmpty(checkOutDate.trim()) -> {
                binding.layoutCheckoutDate.error = "Please Enter Check-out date"
                return false
            }
            TextUtils.isEmpty(noOfPerson.trim()) -> {
                binding.layoutNosGuest.error = "Please Enter No of Guest"
                return false
            }
            TextUtils.isEmpty(roomNo.trim()) -> {
                binding.layoutRoomNo.error = "Please Enter Room No"
                return false
            }
            TextUtils.isEmpty(imagePath.trim()) -> {
                toast("Please select photo Id")
                return false
            }

            else -> return true
        }
    }


    class FormViewModelFactory(private val formRepo: FormRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormViewModel(formRepo) as T
        }
    }
}