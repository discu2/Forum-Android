package com.example.discuzandoird.service

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class ApiService {

    data class LoginRequest(

        @SerializedName("username")
        val username: String,
        @SerializedName("password")
        val password: String

    )

    data class LoginResponse(
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("expireIn")
        val expireIn: String,
        @SerializedName("expireDateTime")
        val expireDateTime: String,
        @SerializedName("refreshToken")
        val refreshToken: String
    )

    private val url = "http://192.168.0.101:8080/"


    fun login(): String {
        return url + "account/login"
    }

    fun getAccessToken(): String {
        return url + "oauth/refresh_token"
    }

}