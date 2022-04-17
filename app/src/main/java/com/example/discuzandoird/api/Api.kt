package com.example.discuzandoird.api

import com.google.gson.annotations.SerializedName

class Api {

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