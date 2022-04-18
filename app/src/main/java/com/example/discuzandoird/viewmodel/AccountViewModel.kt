package com.example.discuzandoird.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.android.volley.*

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.discuzandoird.bean.AccountBean
import com.example.discuzandoird.api.AccountService
import com.example.discuzandoird.singleton.VolleySingleton
import com.google.gson.Gson

import org.json.JSONObject


class AccountViewModel(application: Application) : AndroidViewModel(application) {

    var accountBean = MutableLiveData<AccountBean>().also {
        it.value = AccountBean()
    }

    val accountService = AccountService(getApplication())

    fun oauth(action: () -> Unit) {

        val stringRequest = object : StringRequest(
            Method.GET,
            accountService.getAccessToken(),
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
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)

    }

    fun updateAccountBean() {

        accountBean.postValue(this.accountBean.value)

    }

}