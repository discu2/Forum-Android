package com.example.discuzandoird.api

import android.app.Application
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.discuzandoird.singleton.VolleySingleton
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject

class ApiService constructor(application: Application) {

    data class LoginRequest(
        @SerializedName("username")
        val username: String,
        @SerializedName("password")
        val password: String
    )

    data class RegisterRequest(
        @SerializedName("mail")
        val mail: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("password")
        val password: String,
    )

    private val application = application

    private val url = "http://192.168.0.101:8080/"

    fun login(): String {
        return url + "account/login"
    }

    fun register(): String {
        return url + "account/register"
    }

    fun getAccount(username: String): String {
        return url + "account/" + username
    }

    fun getAccessToken(): String {
        return url + "oauth/refresh_token"
    }

    fun getBoardList(): String {
        return url + "board"
    }

    fun fetchJsonObject(
        method: Int,
        url: String,
        request: JSONObject?,
        accessToken: String?,
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
            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
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

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                if (accessToken != null) {
                    headers["Authorization"] = "Bearer $accessToken"
                }
                return headers
            }
        }
        VolleySingleton.getInstance(application).requestQueue.add(jsonObjectRequest)
    }

    fun fetchJsonArray(
        method: Int,
        url: String,
        request: JSONArray?,
        accessToken: String?,
        response: (jsonArray: JSONArray?) -> Unit,
        error: (volleyError: VolleyError?) -> Unit
    ) {
        val jsonArrayRequest = object : JsonArrayRequest(
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
            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONArray> {
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

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                if (accessToken != null) {
                    headers["Authorization"] = "Bearer $accessToken"
                }
                return headers
            }
        }
        VolleySingleton.getInstance(application).requestQueue.add(jsonArrayRequest)
    }
}