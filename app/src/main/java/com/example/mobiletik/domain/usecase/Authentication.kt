package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.mobiletik.presentation.view.LoginActivity
import com.example.mobiletik.presentation.view.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Authentication {

    private val auth = Firebase.auth

    fun checkUser(mActivity : Activity) {
        if (auth.currentUser != null) {
            mActivity.startActivity(Intent(mActivity, MainActivity::class.java))
            mActivity.finish()
        }
    }

    fun getUID() : String {
        val uid = auth.currentUser!!.uid
        return uid
    }

    fun signOut(mActivity : Activity) {
        auth.signOut()
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        try {
            sharedPref.edit().clear().apply()
        } catch (e : Exception) {
            Toast.makeText(mActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        mActivity.startActivity(Intent(mActivity, LoginActivity::class.java))
        mActivity.finish()
    }
}