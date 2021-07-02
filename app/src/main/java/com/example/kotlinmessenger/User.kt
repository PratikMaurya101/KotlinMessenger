package com.example.kotlinmessenger

class User(val uid: String, val username: String, val profileImageUrl: String){
    constructor(): this("","","")
}
