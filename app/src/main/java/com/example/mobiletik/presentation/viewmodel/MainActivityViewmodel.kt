package com.example.mobiletik.presentation.viewmodel

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mobiletik.model.data.Chat
import com.example.mobiletik.presentation.adapter.ChatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MainActivityViewmodel : ViewModel() {

    val chatData: MutableList<Chat> = mutableListOf()
    val adapter = ChatAdapter(chatData)

    init {
        GlobalScope.launch(Dispatchers.Default) {
            Firebase.database.getReference("Chat").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "onDataChange: Data berubah")
                    if (snapshot.exists()) {
                        if (chatData.isEmpty()) {
                            for (datasnapshot in snapshot.children) {
                                val content = datasnapshot.child("message").value as String
                                val senderUID = datasnapshot.child("senderUID").value as String
                                val senderName = datasnapshot.child("senderName").value as String
                                chatData.add(Chat(senderUID, senderName, content))
                                adapter.notifyItemInserted(chatData.size - 1)
                            }
                            Log.d(TAG, "onDataChange: Data dimuat dari kosong")
                        } else {
                            with(snapshot.children.last()) {
                                val content = this.child("message").value as String
                                val senderUID = this.child("senderUID").value as String
                                val senderName = this.child("senderName").value as String
                                chatData.add(Chat(senderUID, senderName, content))
                                adapter.notifyItemInserted(chatData.size - 1)
                            }
                            Log.d(TAG, "onDataChange: Data ditambahkan")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: $error.message")
                }
            })
        }
    }

    override fun onCleared() {
        chatData.clear()
        super.onCleared()
    }
}