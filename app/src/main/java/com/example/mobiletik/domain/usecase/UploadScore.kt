package com.example.mobiletik.domain.usecase

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

@DelicateCoroutinesApi
object UploadScore {

    fun uploadQuizScoreIntoFirestore(quizTitle : String, Score : Int) {
        val handler = CoroutineExceptionHandler { _, exception ->
            uploadQuizScoreIntoFirestore(quizTitle, Score)
        }
        GlobalScope.launch {
            Log.d(ContentValues.TAG, "updateQuizScoreIntoFirestore: Fungsi dipanggil")
            val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1)
            Log.d(ContentValues.TAG, "updateQuizScoreInFirestore: index push $newIndex")
            withContext(Dispatchers.IO + handler){
                Firebase.firestore.collection("Users").document(Authentication.getUID()).update(newIndex, Score).await()
            }
        }
    }
}