package com.example.mobiletik.model

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.mobiletik.model.Authentication.getUID
import com.example.mobiletik.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Database {
    private val database = Firebase.database

    fun checkUser(mActivity : Activity) {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)

        val userName = sharedPref.getString("userName", "")
        val userNis = sharedPref.getString("userNis", "")
        val userEmail = sharedPref.getString("userEmail", "")

        if (userName!!.isEmpty() || userNis!!.isEmpty() || userEmail!!.isEmpty()) {
            setupUser(mActivity)
        } else {
            greetUser(mActivity)
        }
    }

    private fun setupUser(mActivity : Activity) {

        val loading = Loading(mActivity)
        loading.startLoading()

        database.getReference("User").child(getUID()).child("Profile").get().addOnSuccessListener { snapshot ->

            val nama = snapshot.child("nama").value.toString()
            val nis = snapshot.child("nis").value.toString()
            val email = snapshot.child("email").value.toString()

            val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("userName", nama)
                putString("userEmail", email)
                putString("userNis", nis)
                apply()
            }
            greetUser(mActivity)
            loading.dismissLoading()
        }.addOnFailureListener {
            loading.dismissLoading()
            Toast.makeText(mActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun greetUser(mActivity : Activity) {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "")
        Toast.makeText(mActivity, "Selamat Datang $userName", Toast.LENGTH_SHORT).show()
    }

    fun userData(mActivity : Activity) : UserInfo {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val userName : String = sharedPref.getString("userName", "").toString()
        val userNis : String = sharedPref.getString("userNis", "").toString()
        val userEmail = sharedPref.getString("userEmail", "").toString()
        return UserInfo(userName, userNis, userEmail)
    }
}