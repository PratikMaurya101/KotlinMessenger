package com.example.kotlinmessenger.models

class User(val uid: String, val username: String, val profileImageUrl: String){
    constructor(): this("","","")
}
