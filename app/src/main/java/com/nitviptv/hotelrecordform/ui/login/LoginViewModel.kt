package com.nitviptv.hotelrecordform.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var loginResult: MutableLiveData<Boolean> = MutableLiveData()


    fun doLogin(userName: String, password: String) {
        //call api
        viewModelScope.launch {
            delay(3000)
            loginResult.value = false
        }
    }
}