package com.example.mobiletik.domain.usecase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object UpdateAttemptIntoFirestore {
    fun updateAttemptIntoFirestore(quizTitle: String, currentAttempt: Long) {
        val handler = CoroutineExceptionHandler { _, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                updateAttemptIntoFirestore(quizTitle, currentAttempt)
            }
        }
        GlobalScope.launch {
            withContext(Dispatchers.IO + handler) {
                val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1) + "Attempt"
                Firebase.firestore.collection("Users").document(Authentication.getUID())
                    .update(newIndex, currentAttempt).await()
            }
        }
    }
}