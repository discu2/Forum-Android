package com.example.discuzandoird.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.VolleyError
import com.example.discuzandoird.api.ApiService
import com.example.discuzandoird.bean.BoardBean
import com.google.gson.Gson

class HomeViewModel constructor(application: Application) : AndroidViewModel(application) {

    var boardBeanList = MutableLiveData<List<BoardBean>>()

    private val apiService = ApiService(getApplication())

    fun getBoardList(accessToken: String?, e: (volleyError: VolleyError?) -> Unit) {

        apiService.fetchJsonArray(
            Request.Method.GET,
            apiService.getBoardList(),
            null,
            accessToken,
            {
                if (it != null) {
                    val list: MutableList<BoardBean> = mutableListOf()
                    for (i in 0 until it.length()) {
                        val boardBean =
                            Gson().fromJson(it.get(i)?.toString(), BoardBean::class.java)
                        list.add(boardBean)
                    }
                    boardBeanList.postValue(list)
                }

            },
            {
                e(it)
            }
        )

    }

}