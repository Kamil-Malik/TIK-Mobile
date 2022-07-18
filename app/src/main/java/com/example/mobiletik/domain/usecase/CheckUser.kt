package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Intent
import com.example.mobiletik.presentation.view.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CheckUser {

    fun checkLogin(mActivity: Activity) {
        if (Firebase.auth.currentUser != null) {
            Intent(mActivity, MainActivity::class.java).also {
                CoroutineScope(Dispatchers.Main).launch {
                    SaveIntoSharedpref.saveData(mActivity)
                }
                mActivity.startActivity(it)
                mActivity.finish()
            }
        }
    }
}