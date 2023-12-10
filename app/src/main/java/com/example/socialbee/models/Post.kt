package com.example.socialbee.models


data class Post(
    val text:String = "",
    val author: User,
    val timeStamp:Long = 0L,
    val likes: ArrayList<String> = ArrayList<String>()
)