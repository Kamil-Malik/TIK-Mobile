package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object SaveDataFromFirestore {

    fun checkUser(mActivity : Activity) {
        if (checkProfile(mActivity)) {
            greetUser(mActivity)
        }
        if (!checkProfile(mActivity)) {
            saveData(mActivity)
        }
    }

    private fun checkProfile(mActivity : Activity) : Boolean {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        with(sharedPref) {
            val userName = getString("userName", "")!!
            val userNis = getString("userNIS", "")!!
            val userEmail = getString("userEmail", "")!!
            if (userName.isEmpty()) {
                return false
            } else if (userNis.isEmpty()) {
                return false
            } else if (userEmail.isEmpty()) {
                return false
            }
            return true
        }
    }

    fun saveData(mActivity : Activity) {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "saveData: $exception")
            saveData(mActivity)
        }
        CoroutineScope(Dispatchers.IO + handler).launch {
            val document = Firebase.firestore.collection("Users").document(Authentication.getUID())
                .get().await()
            withContext(Dispatchers.Main + handler) {
                if (document.exists()) {
                    val sharedPreferences =
                        mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
                    with(sharedPreferences) {
                        putString("userName", document.data!!["userName"] as String)
                        putString("userEmail", document.data!!["userEmail"] as String)
                        putString("userNIS", document.data!!["userNIS"] as String)
                        putLong("kuisSatu", document.data!!["kuisSatu"] as Long)
                        putLong("kuisDua", document.data!!["kuisDua"] as Long)
                        putLong("kuisTiga", document.data!!["kuisTiga"] as Long)
                        putLong("kuisEmpat", document.data!!["kuisEmpat"] as Long)
                        putLong("kuisLima", document.data!!["kuisLima"] as Long)
                        apply()
                    }
                    greetUser(mActivity)
                } else return@withContext
            }
        }
    }
    
    fun greetUser(mActivity : Activity){
        val userName = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            .getString("userName", "")!!
        Toast.makeText(mActivity, "Selamat Datang $userName", Toast.LENGTH_SHORT).show()
    }
}
