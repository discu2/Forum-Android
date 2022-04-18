package com.example.discuzandoird.bean

class AccountBean {

    data class AuthBean (

        var accessToken: String? = null,
        var refreshToken: String? = null,
        var isLoggedIn: Boolean = false

    )

    var username: String? = null
    var email: String? = null
    var nickname: String? = null
    var roleIds: List<String>? = null
    var auth = AuthBean()

}