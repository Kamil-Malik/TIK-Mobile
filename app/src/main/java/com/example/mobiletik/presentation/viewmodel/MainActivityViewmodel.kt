package com.example.mobiletik.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.mobiletik.model.usecase.DatabaseUser

class MainActivityViewmodel : ViewModel() {
    fun loadProfile(mActivity : Activity) {
        val database = DatabaseUser
        database.checkUser(mActivity)
//        database.getQuizzScore(mActivity)
    }
}