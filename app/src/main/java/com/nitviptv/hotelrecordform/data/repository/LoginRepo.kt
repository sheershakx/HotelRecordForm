package com.nitviptv.hotelrecordform.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nitviptv.hotelrecordform.data.ApiInterface
import com.nitviptv.hotelrecordform.domain.login.LoginResponseData
import java.lang.Exception

class LoginRepo(private val apiInterface: ApiInterface) {

    private var loginResultPrivate = MutableLiveData<LoginResponseData>()

    val loginResult: LiveData<LoginResponseData>
        get() = loginResultPrivate

    suspend fun doVendorLogin(params: Params) {
        try {
            var result = apiInterface.doVendorLogin(params.username, params.password)
            if (result != null) {
                loginResultPrivate.postValue(result)
            }
        }
        catch (e:Exception){
            Log.d("Sheershak", "api error: ${e.message} ")
        }


    }

    data class Params(val username: String, val password: String)
}