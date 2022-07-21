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

    fun Logout(mActivity: Activity) {
        Firebase.auth.signOut()
        mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit().clear().commit()
        Intent(mActivity, LoginActivity::class.java).also {
            mActivity.startActivity(it)
            mActivity.finish()
            Log.d(TAG, "Logout: Logout berhasil")
        }
    }
}