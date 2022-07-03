package com.example.mobiletik.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.mobiletik.model.Database

class MainActivityViewmodel : ViewModel() {
    fun loadProfile(mActivity : Activity) {
        val database = Database
        database.checkUser(mActivity)
//        database.getQuizzScore(mActivity)
    }
}