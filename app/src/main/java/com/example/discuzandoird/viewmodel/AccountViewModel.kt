package com.example.discuzandoird.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.discuzandoird.bean.AccountBean
import com.example.discuzandoird.api.AccountService
import com.example.discuzandoird.singleton.VolleySingleton
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    var accountBean = MutableLiveData<AccountBean>().also {
        it.value = AccountBean()
    }

    fun fetchApi(
        method: Int,
        url: String,
        request: JSONObject,
        response: (jsonObject: JSONObject?) -> Unit,
        error: (volleyError: VolleyError?) -> Unit
    ) {
        val jsonObjectRequest = object : JsonObjectRequest(
            method,
            url,
            request,
            {
                response(it)
            },
            {
                error(it)
            }
        ) {

            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject>? {

                if (response.data.isEmpty()) {
                    val responseData = "{}".encodeToByteArray()
                    val newResponse = NetworkResponse(
                        response.statusCode,
                        responseData,
                        response.headers,
                        response.notModified
                    )
                    return super.parseNetworkResponse(newResponse)
                }
                return super.parseNetworkResponse(response)

            }

        }
        VolleySingleton.getInstance(getApplication()).requestQueue.add(jsonObjectRequest)
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
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)

    }

    fun updateAccountBean() {

        accountBean.postValue(this.accountBean.value)

    }

}