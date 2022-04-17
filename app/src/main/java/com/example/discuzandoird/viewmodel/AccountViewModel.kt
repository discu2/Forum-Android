package com.example.discuzandoird.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.discuzandoird.model.AccountBean
import com.example.discuzandoird.repository.ApiRepository
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

        val loginRequest = ApiRepository.LoginRequest(username, password)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            ApiRepository().login(),
            JSONObject(Gson().toJson(loginRequest)),
            {
                var loginResponse =
                    Gson().fromJson(it.toString(), ApiRepository.LoginResponse::class.java)
                accountRepository.value?.auth?.accessToken = loginResponse.accessToken
                accountRepository.value?.auth?.refreshToken = loginResponse.refreshToken
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

    private fun updateAccountRepository() {
        accountRepository.postValue(this.accountRepository.value)
    }

}