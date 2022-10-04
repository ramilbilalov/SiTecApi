package com.bilalov.testforsitec.network

import com.bilalov.testforsitec.data.ResponseSearchAuth
import com.bilalov.testforsitec.data.ResponseUserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/UKA_TRADE/hs/MobileClient/{imei}/authentication/")
    fun getAuthInList(
        @Header("Authorization") authorization: String,
        @Path("imei") imei: String,
        @Query("uid ") uid: String,
        @Query("pass") pass: String,
        @Query("copyFromDevice") copyFromDevice: String,
        @Query("nfc") nfc: String,
    ): Call<ResponseSearchAuth>

    @GET("/UKA_TRADE/hs/MobileClient/{imei}/form/users?")
    fun getQuestListProfile(
        @Header("Authorization") authorization: String,
        @Path("imei") imei:String,
    ): Call<ResponseUserProfile>

}
