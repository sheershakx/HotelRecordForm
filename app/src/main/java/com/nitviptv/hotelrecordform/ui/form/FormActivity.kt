package com.nitviptv.hotelrecordform.ui.form

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.nitviptv.hotelrecordform.utils.PermissionUtils
import com.nitviptv.hotelrecordform.utils.toast
import com.nitviptv.hotelrecordform.utils.transformIntoDatePicker
import com.nitviptv.hotelrecordform.utils.transformIntoTimePicker
import java.util.jar.Manifest

class FormActivity : AppCompatActivity() {
    lateinit var binding: ActivityFormBinding
    lateinit var viewModel: FormViewModel

    companion object {
        private const val CAMERA_REQ_CODE = 1001
        private const val GALLERY_REQ_CODE = 1002
        private const val CAMERA_PERMISSION_REQ_CODE = 10001
        private const val STORAGE_PERMISSION_REQ_CODE = 10002

    }

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

            AlertDialog.Builder(this)
                .setTitle("Choose Image Provider")
                .setMessage("Capture from camera or pick from gallery?")
                .setPositiveButton(
                    "Gallery",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        if (checkPermission(
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                STORAGE_PERMISSION_REQ_CODE
                            )
                        ) {
                            openGallery()
                        }

                    })
                .setNegativeButton("Camera", DialogInterface.OnClickListener { dialogInterface, i ->
                    if (checkPermission(
                            android.Manifest.permission.CAMERA,
                            CAMERA_PERMISSION_REQ_CODE
                        )
                    ) {
                        openCamera()
                    }
                })
                .show()


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

    private fun checkPermission(permission: String, reqCode: Int): Boolean {
        return if (PermissionUtils.hasPermission(this, permission)) {
            true
        } else {
            PermissionUtils.requestPermissions(
                this,
                arrayOf(permission),
                reqCode
            )
            false
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


    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, 1001)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->

            startActivityForResult(intent, CAMERA_REQ_CODE)

        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"

            startActivityForResult(intent, GALLERY_REQ_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQ_CODE) {
                val bitmap = data?.extras?.get("data") as Bitmap
//                ivImage.setImageBitmap(bitmap)
            } else if (requestCode == GALLERY_REQ_CODE) {
                val uri = data?.data
//                ivImage.setImageURI(uri)
            }
        }
    }
}


class FormViewModelFactory(private val formRepo: FormRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormViewModel(formRepo) as T
    }

}