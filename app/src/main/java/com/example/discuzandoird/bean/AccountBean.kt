package com.example.discuzandoird.bean

data class AccountBean(

    var username: String? = null,
    var email: String? = null,
    var nickname: String? = null,
    var roleIds: List<String>? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var isLoggedIn: Boolean = false

)