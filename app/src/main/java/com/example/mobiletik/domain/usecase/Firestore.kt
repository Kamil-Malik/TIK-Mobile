package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mobiletik.model.utility.Loading
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object Firestore {
    val ref = Firebase.firestore

    fun checkUser(mActivity : Activity) {
        if (checkProfile(mActivity)) {
            greetUser(mActivity)
        }
        if (!checkProfile(mActivity)) {
            getUserDataFromFirestore(mActivity)
        }
    }

    private fun getUserDataFromFirestore(mActivity : Activity) {
        Log.d(TAG, "getUserDataFromFirestore: Fungsi dipanggil")
        val loading = Loading(mActivity)
        loading.startLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val userDocument =
                Firebase.firestore.collection("Users").document(Authentication.getUID()).get()
                    .await()
            Log.d(TAG, "getUserDataFromFirestore: Dokumen berhasil didapatkan")
            withContext(Dispatchers.Main) { //Pindah ke Main DIspatcher
                if (userDocument.exists()) {
                    Log.d(TAG, "getUserDataFromFirestore: ${userDocument.data}")
                    val sharedPreferences =
                        mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
                    with(sharedPreferences) {
                        putString("userName", userDocument.data!!["userName"] as String)
                        putString("userEmail", userDocument.data!!["userEmail"] as String)
                        putString("userNIS", userDocument.data!!["userNIS"] as String)
                        putLong("kuisSatu", userDocument.data!!["kuisSatu"] as Long)
                        putLong("kuisDua", userDocument.data!!["kuisDua"] as Long)
                        putLong("kuisTiga", userDocument.data!!["kuisTiga"] as Long)
                        putLong("kuisEmpat", userDocument.data!!["kuisEmpat"] as Long)
                        putLong("kuisLima", userDocument.data!!["kuisLima"] as Long)
                        apply()
                    }
                    Log.d(TAG, "getUserDataFromFirestore: Sharedpref telah diisi")
                    greetUser(mActivity)
                }
                loading.dismissLoading()
            }
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

    private fun greetUser(mActivity : Activity) {
        val userName = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            .getString("userName", "")
        Toast.makeText(mActivity, "Selamat Datang $userName", Toast.LENGTH_SHORT).show()
    }

    fun updateQuizScoreInFirestore(mActivity : Activity, index : String, score : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "updateQuizScoreIntoFirestore: Fungsi dipanggil")
            val newIndex = index[0].lowercase() + index.removeRange(0, 1)
            Log.d(TAG, "updateQuizScoreInFirestore: index push $newIndex")
            withContext(Dispatchers.IO) {
                ref.collection("Users").document(Authentication.getUID()).update(newIndex, score)
                    .await()
                Log.d(
                    TAG,
                    "updateQuizScoreIntoFirestore: $newIndex berhasil diupdate dengan nilai ${score}"
                )
                withContext(Dispatchers.Main) {
                    async {
                        UserData.saveQuizResultIntoSharedPref(mActivity, newIndex, score)
                    }.await()
                    Log.d(TAG, "updateQuizScoreIntoFirestore: Proses telah selesai")
                    mActivity.finish()
                }
            }
        }
    }
}