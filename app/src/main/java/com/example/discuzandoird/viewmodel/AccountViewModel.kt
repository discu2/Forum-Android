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

    val apiService = ApiService(getApplication())

    fun updateAccountBean() {

        accountBean.postValue(this.accountBean.value)

    }

    fun login(
        username: String,
        password: String,
        r: () -> Unit,
        e: (volleyError: VolleyError?) -> Unit
    ) {

        apiService.fetchApi(
            Request.Method.POST,
            apiService.login(),
            JSONObject(
                Gson().toJson(ApiService.LoginRequest(username, password))
            ),
            null,
            {

                val response =
                    Gson().fromJson(it.toString(), ApiService.LoginResponse::class.java)
                accountBean.value?.auth?.accessToken = response.accessToken
                accountBean.value?.auth?.refreshToken = response.refreshToken
                accountBean.value?.username = username
                accountBean.value?.auth?.isLoggedIn = true
                updateAccountBean()
                r()

            },
            {

                e(it)

            }
        )

    }

    fun oauthCheck() {

        apiService.fetchApi(
            Request.Method.GET,
            apiService.getAccessToken(),
            null,
            accountBean.value?.auth?.refreshToken,
            {},
            {}
        )

    }

}