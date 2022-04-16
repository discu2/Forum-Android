package com.example.discuzandoird.model

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.discuzandoird.service.ApiService
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    var accessToken: String? = null
    var refreshToken: String? = null
    var isLoggedIn: Boolean = false

}