package com.example.mobiletik.domain.usecase

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object GetQuestionTitle {

    fun getTitle(path : String) : String {
        val handler = CoroutineExceptionHandler { _, exception ->
            getTitle(path)
        }
        var title = ""
        CoroutineScope(Dispatchers.IO + handler).launch {
            val _title = Firebase.database.getReference("Kuis").child(path).child("judul").get().await().toString()
            title = _title
            return@launch
        }
        return title
    }
}