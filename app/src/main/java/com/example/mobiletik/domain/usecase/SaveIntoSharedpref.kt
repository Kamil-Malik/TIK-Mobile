package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.mobiletik.domain.usecase.GetUID.getUID
import com.example.mobiletik.model.data.TemplateUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object SaveIntoSharedpref {

    suspend fun saveData(mActivity: Activity) {
        val handler = CoroutineExceptionHandler { _, exception ->
            CoroutineScope(Dispatchers.Main).launch {
                Log.e(TAG, "saveData: $exception")
                saveData(mActivity)
            }
        }
        CoroutineScope(Dispatchers.IO + handler).launch {
            val document = Firebase.firestore.collection("Users").document(getUID())
                .get().await()
            Log.d(TAG, "saveData: $document")

            withContext(Dispatchers.Main + handler) {
                if (document.exists()) {
                    val sharedPreferences =
                        mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
                    with(sharedPreferences) {
                        putString("userName", document.data!!["userName"] as String)
                        putString("userEmail", document.data!!["userEmail"] as String)
                        putString("userNIS", document.data!!["userNIS"] as String)
                        putString("userKelas", document.data!!["userKelas"] as String)
                        putLong("kuisSatu", document.data!!["kuisSatu"] as Long)
                        putLong("kuisDua", document.data!!["kuisDua"] as Long)
                        putLong("kuisTiga", document.data!!["kuisTiga"] as Long)
                        putLong("kuisEmpat", document.data!!["kuisEmpat"] as Long)
                        putLong("kuisLima", document.data!!["kuisLima"] as Long)
                        putLong("kuisSatuAttempt", document.data!!["kuisSatuAttempt"] as Long)
                        putLong("kuisDuaAttempt", document.data!!["kuisDuaAttempt"] as Long)
                        putLong("kuisTigaAttempt", document.data!!["kuisTigaAttempt"] as Long)
                        putLong("kuisEmpatAttempt", document.data!!["kuisEmpatAttempt"] as Long)
                        putLong("kuisLimaAttempt", document.data!!["kuisLimaAttempt"] as Long)
                        apply()
                    }
                    Log.d(TAG, "saveData: Sharepref berhasil diisi dengan data $document")
                    GreetUser.greetUser(mActivity)
                } else return@withContext
            }
        }
    }
}