package com.example.discuzandoird.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.discuzandoird.bean.AccountBean
import com.example.discuzandoird.api.ApiService
import com.google.gson.Gson
import org.json.JSONObject

class AccountViewModel : ViewModel() {

    var accountRepository = MutableLiveData<AccountBean>().also {
        it.value = AccountBean()
    }
    private var queue: RequestQueue? = null

    fun setQueue(queue: RequestQueue) {

        this.queue = queue

    }

    fun login(username: String, password: String) {

        val loginRequest = ApiService.LoginRequest(username, password)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            ApiService().login(),
            JSONObject(Gson().toJson(loginRequest)),
            {
                var response =
                    Gson().fromJson(it.toString(), ApiService.LoginResponse::class.java)
                accountRepository.value?.auth?.accessToken = response.accessToken
                accountRepository.value?.auth?.refreshToken = response.refreshToken
                accountRepository.value?.username = username
                accountRepository.value?.auth?.isLoggedIn = true
                updateAccountRepository()
            },
            {
                println(it)
            }
        )
        queue?.add(jsonObjectRequest)
    }

    fun oauth(action: () -> Unit) {

        val stringRequest = object : StringRequest(
            Method.GET,
            ApiService().getAccessToken(),
            {
                val response =
                    Gson().fromJson(it.toString(), ApiService.LoginResponse::class.java)
                if (response.accessToken != accountRepository.value?.auth?.accessToken) {
                    accountRepository.value?.auth?.accessToken = response.accessToken
                }
                action()
            },
            {
                println(it)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer " + accountRepository.value?.auth?.refreshToken
                return headers
            }
        }
        queue?.add(stringRequest)

    }

    private fun updateAccountRepository() {

        accountRepository.postValue(this.accountRepository.value)

    }

}