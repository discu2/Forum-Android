package com.example.discuzandoird.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.discuzandoird.model.AccountRepository
import com.example.discuzandoird.service.ApiService
import com.google.gson.Gson
import org.json.JSONObject

class AccountViewModel : ViewModel() {

    var accountRepository = MutableLiveData<AccountRepository>().also {
        it.value = AccountRepository()
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
                var loginResponse =
                    Gson().fromJson(it.toString(), ApiService.LoginResponse::class.java)
                accountRepository.value?.auth?.accessToken = loginResponse.accessToken
                accountRepository.value?.auth?.refreshToken = loginResponse.refreshToken
                accountRepository.value?.auth?.isLoggedIn = true
                updateAccountRepository()
            },
            {
                println(it)
            }
        )
        queue?.add(jsonObjectRequest)
    }

    private fun updateAccountRepository() {
        accountRepository.postValue(this.accountRepository.value)
    }

}