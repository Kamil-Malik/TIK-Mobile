package com.example.mobiletik.model.data

import java.util.*

data class Chat(
    val senderUID : String,
    val senderName : String,
    val message : String,
    val timestamp : String = "${
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }:${Calendar.getInstance().get(Calendar.MINUTE)}"
)
