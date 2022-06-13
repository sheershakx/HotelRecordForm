package com.nitviptv.hotelrecordform.data

import com.nitviptv.hotelrecordform.domain.ProductData
import com.nitviptv.hotelrecordform.domain.form.FormData
import com.nitviptv.hotelrecordform.domain.login.LoginResponseData
import com.nitviptv.hotelrecordform.utils.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiInterface {
    companion object {
        fun getRetrofit(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        private const val VENDOR_LOGIN = "VendorLogin/VendorLogin"
        private const val FORM_SAVE = "Form/InsertForm"
    }

    @FormUrlEncoded
    @POST(VENDOR_LOGIN)
    suspend fun doVendorLogin(
        @Field("Username") Username: String,
        @Field("Password") Password: String,
    ): LoginResponseData


    @POST(FORM_SAVE)
    suspend fun saveForm(
        @Body data: FormData
    ): Int
}