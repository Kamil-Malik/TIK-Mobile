package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mobiletik.presentation.view.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Logout {

    fun logout(mActivity: Activity) {
        val shardPref =
            mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit().clear()
                .apply()
        Log.d(TAG, "logout: $shardPref")
        Firebase.auth.signOut()
        Intent(mActivity, LoginActivity::class.java).also {
            mActivity.startActivity(it)
            mActivity.finish()
            Log.d(TAG, "Logout: Logout berhasil")
        }
    }
}