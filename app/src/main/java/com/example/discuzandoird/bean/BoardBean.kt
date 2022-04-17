package com.example.discuzandoird.bean

class BoardBean {

    data class Permission(

        var access: List<String>,
        var post: List<String>,
        var reply: List<String>,
        var comment: List<String>,
        var edit: List<String>
    )

    var id: List<String>? = null
    var name: List<String>? = null
    var groupName: List<String>? = null
    var permissions: List<Permission>? = null

}