package com.nitviptv.hotelrecordform.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nitviptv.hotelrecordform.data.ApiInterface
import com.nitviptv.hotelrecordform.domain.form.FormData

class FormRepo(private val apiInterface: ApiInterface) {

    private val formSaveResponseData = MutableLiveData<Int>()

    val formSaveResponse: LiveData<Int>
        get() = formSaveResponseData


    suspend fun saveForm(params: Params) {
        var formSaveResult = apiInterface.saveForm(params.data)
        if (formSaveResult != null) {
            formSaveResponseData.postValue(formSaveResult)
        }
    }

    data class Params(val data: FormData)


}