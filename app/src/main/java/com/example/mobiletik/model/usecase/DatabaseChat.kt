package com.example.mobiletik.model.usecase

import android.content.Context
import android.widget.Toast
import com.example.mobiletik.model.data.Chat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object DatabaseChat {
    val database = Firebase.database.getReference("Chat")

    fun sendMessage(mContext: Context, content: Chat) {

        /*
        * TODO BUAT TIMESTAMP SEBAGAI PATH BIAR BISA TERUS NAMBAH
        */

        database.child("waktu").setValue(content).addOnSuccessListener {
            Toast.makeText(mContext, "Pesan Berhasil Dikirim", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(mContext, it.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}