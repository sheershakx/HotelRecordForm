package com.nitviptv.hotelrecordform.ui.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitviptv.hotelrecordform.data.repository.FormRepo
import com.nitviptv.hotelrecordform.domain.form.FormData
import kotlinx.coroutines.launch
import java.lang.Exception

class FormViewModel(private val formRepo: FormRepo) : ViewModel() {

    val formResponse: LiveData<Int>
        get() = formRepo.formSaveResponse

    fun saveForm(formData: FormData) {

        viewModelScope.launch {
            try {
                formRepo.saveForm(FormRepo.Params(formData))
            } catch (e: Exception) {
                Log.d("Sheershak", "saveForm: ${e.message}")
            }

        }
    }
}