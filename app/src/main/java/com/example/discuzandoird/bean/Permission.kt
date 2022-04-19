package com.example.discuzandoird.bean

data class Permission(

    var access: List<String>? = null,
    var post: List<String>? = null,
    var reply: List<String>? = null,
    var comment: List<String>? = null,
    var edit: List<String>? = null

)
