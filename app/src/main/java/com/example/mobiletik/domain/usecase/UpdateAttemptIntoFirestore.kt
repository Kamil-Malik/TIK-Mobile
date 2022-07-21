package com.example.mobiletik.domain.usecase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object UpdateAttemptIntoFirestore {
    @OptIn(DelicateCoroutinesApi::class)
    fun updateAttemptIntoFirestore(quizTitle: String, currentAttempt: Long) {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.w(TAG, "updateAttemptIntoFirestore: Update gagal karena ${exception.message}")
            CoroutineScope(Dispatchers.Main).launch {
                updateAttemptIntoFirestore(quizTitle, currentAttempt)
            }
        }
        GlobalScope.launch {
            withContext(Dispatchers.IO + handler) {
                val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1) + "Attempt"
                Firebase.firestore.collection("Users").document(GetUID.getUID())
                    .update(newIndex, currentAttempt).await()
                Log.d(TAG, "updateAttemptIntoFirestore: Update attempt sukses")
            }
        }
    }
}