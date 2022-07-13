package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mobiletik.domain.usecase.Authentication.getUID
import com.example.mobiletik.model.data.ScoreKuis
import com.example.mobiletik.domain.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DatabaseUser {
    private val database = Firebase.database.getReference("User")

    fun checkUser(mActivity : Activity) {
        if (checkProfile(mActivity)) {
            greetUser(mActivity)
        }
        if (!checkProfile(mActivity)) {
            setupUser(mActivity)
        }
    }

    private fun checkProfile(mActivity : Activity) : Boolean {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "")
        val userNis = sharedPref.getString("userNis", "")
        val userEmail = sharedPref.getString("userEmail", "")

        if (userName!!.isEmpty()) {
            return false
        } else if (userNis!!.isEmpty()) {
            return false
        } else if (userEmail!!.isEmpty()) {
            return false
        }
        return true
    }

    private fun setupUser(mActivity : Activity) {
        val loading = Loading(mActivity)
        database.child(getUID()).get().addOnSuccessListener { snapshot ->
            Log.d(TAG, "setupUser: Data berhasil didapatkan")

            //  Data Profile
            val nama = snapshot.child("Profile").child("nama").value.toString()
            val nis = snapshot.child("Profile").child("nis").value.toString()
            val email = snapshot.child("Profile").child("email").value.toString()

            var kuis1 = snapshot.child("Score").child("KuisSatu").value.toString()
            if (kuis1 == "null") {
                kuis1 = "-"
            }
            var kuis2 = snapshot.child("Score").child("KuisDua").value.toString()
            if (kuis2 == "null") {
                kuis2 = "-"
            }
            var kuis3 = snapshot.child("Score").child("KuisTiga").value.toString()
            if (kuis3 == "null") {
                kuis3 = "-"
            }
            var kuis4 = snapshot.child("Score").child("KuisEmpat").value.toString()
            if (kuis4 == "null") {
                kuis4 = "-"
            }
            var kuis5 = snapshot.child("Score").child("KuisLima").value.toString()
            if (kuis5 == "null") {
                kuis5 = "-"
            }
            val sharedPref =
                mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {

                //  Profil
                putString("userName", nama)
                putString("userEmail", email)
                putString("userNis", nis)

                //  Kuis
                putString("KuisSatu", kuis1)
                putString("KuisDua", kuis2)
                putString("KuisTiga", kuis3)
                putString("KuisEmpat", kuis4)
                putString("KuisLima", kuis5)
                apply()
            }
            greetUser(mActivity)
            loading.dismissLoading()
        }.addOnFailureListener { error ->
            loading.dismissLoading()
            Toast.makeText(mActivity, error.message.toString(), Toast.LENGTH_SHORT).show()
            Log.e(TAG, "setupUser: Data gagal didapatkan")
        }
    }

    private fun greetUser(mActivity : Activity) {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "")
        Toast.makeText(mActivity, "Selamat Datang $userName", Toast.LENGTH_SHORT).show()
    }

    fun quizDataOffline(mActivity : Activity) : ScoreKuis {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val kuis1 = sharedPref.getLong("kuisSatu", 0)
        val kuis2 = sharedPref.getLong("kuisDua", 0)
        val kuis3 = sharedPref.getLong("kuisTiga", 0)
        val kuis4 = sharedPref.getLong("kuisEmpat", 0)
        val kuis5 = sharedPref.getLong("kuisLima", 0)
        return ScoreKuis(kuis1, kuis2, kuis3, kuis4, kuis5)
    }

    @DelicateCoroutinesApi
    fun uploadScore(mActivity : Activity, index : String, score : Int) {
        val loading = Loading(mActivity)
        loading.startLoading()
        mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
            .putString(index, score.toString()).apply()
        GlobalScope.launch(Dispatchers.IO) {
            database.child(getUID()).child("Score").child(index)
                .setValue(score.toString())
                .addOnSuccessListener {
                    Log.d(TAG, "uploadScore: Score berhasil diupload")
                    mActivity.finish()
                }.addOnFailureListener {
                    Log.e("Error", it.message.toString())
                }
        }
        loading.dismissLoading()
        mActivity.finish()
    }
}
