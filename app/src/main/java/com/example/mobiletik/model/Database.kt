package com.example.mobiletik.model

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mobiletik.model.Authentication.getUID
import com.example.mobiletik.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Database {
    private val database = Firebase.database

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
        loading.startLoading()

        database.getReference("User").child(getUID()).get().addOnSuccessListener { snapshot ->

            //  Data Profile
            val nama = snapshot.child("Profile").child("nama").value.toString()
            val nis = snapshot.child("Profile").child("nis").value.toString()
            val email = snapshot.child("Profile").child("email").value.toString()

            //  Data Kuis
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
            val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
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
        }
    }

    private fun greetUser(mActivity : Activity) {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "")
        Toast.makeText(mActivity, "Selamat Datang $userName", Toast.LENGTH_SHORT).show()
    }

    fun userDataOffline(mActivity : Activity) : UserInfo {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val userName : String = sharedPref.getString("userName", "").toString()
        val userNis : String = sharedPref.getString("userNis", "").toString()
        val userEmail : String = sharedPref.getString("userEmail", "").toString()
        return UserInfo(userName, userNis, userEmail)
    }

    fun quizDataOffline(mActivity : Activity) : ScoreKuis {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val kuis1 = sharedPref.getString("KuisSatu", "-")!!
        val kuis2 = sharedPref.getString("KuisDua", "-")!!
        val kuis3 = sharedPref.getString("KuisTiga", "-")!!
        val kuis4 = sharedPref.getString("KuisEmpat", "-")!!
        val kuis5 = sharedPref.getString("KuisLima", "-")!!
        return ScoreKuis(kuis1, kuis2, kuis3, kuis4, kuis5)
    }

    fun uploadScore(mActivity : Activity, index : String, score : Int) {
        val loading = Loading(mActivity)
        loading.startLoading()
        mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
            .putString(index, score.toString()).apply()
        database.getReference("User").child(getUID()).child("Score").child(index)
            .setValue(score.toString())
            .addOnSuccessListener {
                loading.dismissLoading()
                mActivity.finish()
            }.addOnFailureListener {
                Log.e("Error", it.message.toString())
                loading.dismissLoading()
                mActivity.finish()
            }
    }
}