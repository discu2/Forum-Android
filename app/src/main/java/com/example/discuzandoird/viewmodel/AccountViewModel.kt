package com.example.discuzandoird.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.VolleyError
import com.example.discuzandoird.bean.AccountBean
import com.example.discuzandoird.api.ApiService
import com.google.gson.Gson
import org.json.JSONObject


class AccountViewModel(application: Application) : AndroidViewModel(application) {

    var accountBean = MutableLiveData<AccountBean>().also {
        it.value = AccountBean()
    }

    private val apiService = ApiService(getApplication())

    fun login(
        username: String,
        password: String,
        r: () -> Unit,
        e: (volleyError: VolleyError?) -> Unit
    ) {
        apiService.fetchJsonObject(
            Request.Method.POST,
            apiService.login(),
            JSONObject(
                Gson().toJson(ApiService.LoginRequest(username, password))
            ),
            null,
            {
                val response =
                    Gson().fromJson(it.toString(), AccountBean::class.java)
                accountBean.value?.accessToken = response.accessToken
                accountBean.value?.refreshToken = response.refreshToken
                accountBean.value?.username = username
                accountBean.value?.isLoggedIn = true
                accountBean.postValue(this.accountBean.value)
                r()
            },
            {
                e(it)
            }
        )
    }

    fun register(
        mail: String,
        username: String,
        password: String,
        r: () -> Unit,
        e: (volleyError: VolleyError?) -> Unit
    ) {
        apiService.fetchJsonObject(
            Request.Method.POST,
            apiService.register(),
            JSONObject(
                Gson().toJson(ApiService.RegisterRequest(mail, username, password))
            ),
            null,
            {
                r()
            },
            {
                e(it)
            }
        )
    }

    fun oauthCheck(
        r: (string: String?) -> Unit,
        e: (volleyError: VolleyError?) -> Unit
    ) {
        apiService.fetchJsonObject(
            Request.Method.GET,
            apiService.getAccessToken(),
            null,
            accountBean.value?.refreshToken,
            {
                r(accountBean.value?.accessToken)
            },
            {
                e(it)
            }
        )
    }

}