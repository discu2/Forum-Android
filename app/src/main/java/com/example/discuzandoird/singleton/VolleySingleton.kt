package com.example.discuzandoird.singleton

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(context: Context) {

    companion object {
        private var instance: VolleySingleton? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                VolleySingleton(context).also { instance = it }
            }
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }


}