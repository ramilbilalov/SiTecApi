package com.bilalov.testforsitec.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bilalov.testforsitec.data.ResponseSearchAuth
import com.bilalov.testforsitec.data.ResponseUserProfile
import com.bilalov.testforsitec.network.ApiService
import com.bilalov.testforsitec.network.UrlItemProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :ViewModel() {

    val statusApiAuth = MutableLiveData<String>()
    val statusApiFirst = MutableLiveData<String>()

    private val _allSearchItems = MutableLiveData<ResponseSearchAuth?>()
    val allSearchItems: MutableLiveData<ResponseSearchAuth?>
        get() = _allSearchItems


    private val _allUserInfo = MutableLiveData<ResponseUserProfile?>()
    val allUserInfo: MutableLiveData<ResponseUserProfile?>
        get() = _allUserInfo


    fun getRepository(uid: String, password: String, application: Application, imei:String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendAuth(uid = uid, password = password, application = application, imei = imei)
        }
    }

    fun getUserInfo(imei: String, application: Application) {
        viewModelScope.launch(Dispatchers.IO) {
            getListUsers(imei = imei, application = application)
        }
    }

    private fun sendAuth(password: String, uid: String, application: Application, imei: String) {
        val service = UrlItemProvider.retrofitInstance?.create(ApiService::class.java)
        val call: Call<ResponseSearchAuth>? = service?.getAuthInList(Credentials.basic("http","http"), imei = imei, uid = uid, pass = password, "false","")
        call?.enqueue(object: Callback<ResponseSearchAuth> {
            override fun onResponse(
                call: Call<ResponseSearchAuth>,
                response: Response<ResponseSearchAuth>
            ) {
                Log.e("TTT", response.toString())
                _allSearchItems.postValue(response.body())
                statusApiAuth.value = response.code().toString()
            }
            override fun onFailure(call: Call<ResponseSearchAuth>, t: Throwable) {
                statusApiAuth.value = call.isCanceled.toString()
                Toast.makeText(
                    application,
                    "Упс... Соединение отсутвует",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

 private fun getListUsers(application: Application, imei: String) {
     val service = UrlItemProvider.retrofitInstance?.create(ApiService::class.java)
        val call: Call<ResponseUserProfile>? = service?.getQuestListProfile(Credentials.basic("http","http"), imei = imei)
        call?.enqueue(object: Callback<ResponseUserProfile> {
            override fun onResponse(
                call: Call<ResponseUserProfile>,
                response: Response<ResponseUserProfile>
            ) {
                Log.e("TTT", response.toString())
                statusApiFirst.value = response.isSuccessful.toString()
                _allUserInfo.postValue(response.body())
            }
            override fun onFailure(call: Call<ResponseUserProfile>, t: Throwable) {
                statusApiFirst.value = call.isCanceled.toString()
                _allUserInfo.value = null
                Toast.makeText(
                    application,
                    "Упс... Соединение отсутвует",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}