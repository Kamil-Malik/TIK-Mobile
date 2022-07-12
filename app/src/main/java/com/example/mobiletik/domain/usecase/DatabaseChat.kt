package com.example.mobiletik.domain.usecase

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.mobiletik.model.data.Chat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

object DatabaseChat {
    val database = Firebase.database.getReference("Chat")
    fun sendMessage(content : Chat) {
        CoroutineScope(Dispatchers.IO).launch {
            database.push().setValue(content).addOnSuccessListener {
                Log.d(TAG, "sendMessage: Pesan berhasil dikirimkan")
            }.addOnFailureListener {
                Log.e(TAG, "sendMessage: Pesan gagal dikirimkan. Sebab ${it.message.toString()}")
            }
        }
    }
}