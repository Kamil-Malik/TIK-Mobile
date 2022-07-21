package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.mobiletik.domain.usecase.ToastFunction.toastLong
import com.example.mobiletik.presentation.view.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object CheckUser {

    fun checkLogin(mActivity: Activity) {
        if (Firebase.auth.currentUser != null) {
            val name = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
                .getString("userName", "")
            toastLong(mActivity, "Selamat Datang $name")
            Intent(mActivity, MainActivity::class.java).also {
                mActivity.startActivity(it)
                mActivity.finish()
            }
        }
    }
}