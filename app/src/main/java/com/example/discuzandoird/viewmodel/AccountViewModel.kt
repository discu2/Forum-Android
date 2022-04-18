package com.example.discuzandoird.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.discuzandoird.bean.AccountBean
import com.example.discuzandoird.api.AccountService
import com.google.gson.Gson
import org.json.JSONObject

class AccountViewModel : ViewModel() {

    var accountBean = MutableLiveData<AccountBean>().also {
        it.value = AccountBean()
    }

    var isRegistered = MutableLiveData<Boolean>()

    private var queue: RequestQueue? = null

    fun setQueue(queue: RequestQueue) {

        this.queue = queue

    }

    fun login(username: String, password: String) {

        val loginRequest = AccountService.LoginRequest(username, password)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            AccountService().login(),
            JSONObject(Gson().toJson(loginRequest)),
            {
                val response =
                    Gson().fromJson(it.toString(), AccountService.LoginResponse::class.java)
                accountBean.value?.auth?.accessToken = response.accessToken
                accountBean.value?.auth?.refreshToken = response.refreshToken
                accountBean.value?.username = username
                accountBean.value?.auth?.isLoggedIn = true
                getAccount()
            },
            {
                println(it)
            }
        )
        queue?.add(jsonObjectRequest)
    }

    fun register(mail: String, username: String, password: String) {

    }

    private fun getAccount() {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            accountBean.value?.username?.let { AccountService().getAccount(it) },
            null,
            {
                val response =
                    Gson().fromJson(it.toString(), AccountService.AccountResponse::class.java)
                accountBean.value?.username = response.username
                accountBean.value?.nickname = response.nickname
                accountBean.value?.roleIds = response.roleIds
                println(response.username)
                println(response.nickname)
                println(response.roleIds)
                updateAccountBean()
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
            AccountService().getAccessToken(),
            {
                val response =
                    Gson().fromJson(it.toString(), AccountService.LoginResponse::class.java)
                if (response.accessToken != accountBean.value?.auth?.accessToken) {
                    accountBean.value?.auth?.accessToken = response.accessToken
                }
                action()
            },
            {
                println(it)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer " + accountBean.value?.auth?.refreshToken
                return headers

            }
        }
        queue?.add(stringRequest)

    }

    private fun updateAccountBean() {

        accountBean.postValue(this.accountBean.value)

    }

}