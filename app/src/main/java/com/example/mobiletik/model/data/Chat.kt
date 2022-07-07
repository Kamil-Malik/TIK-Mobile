package com.example.mobiletik.model.data

import com.example.mobiletik.model.usecase.Authentication

data class Chat(
    val senderUID: String,
    val senderName: String,
    val message: String
)
