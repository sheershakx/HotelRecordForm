package com.nitviptv.hotelrecordform.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitviptv.hotelrecordform.data.repository.LoginRepo
import com.nitviptv.hotelrecordform.domain.login.LoginResponseData
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    val loginResult: LiveData<LoginResponseData>
        get() = loginRepo.loginResult


    fun doLogin(username: String, password: String) {
        //call api
        viewModelScope.launch {
            loginRepo.doVendorLogin(LoginRepo.Params(username, password))
        }
    }
}