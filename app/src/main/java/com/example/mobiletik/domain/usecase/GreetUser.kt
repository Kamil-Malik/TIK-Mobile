package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Context
import android.widget.Toast

object GreetUser {

    fun greetUser(mActivity : Activity){
        val userName = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            .getString("userName", "")!!
        Toast.makeText(mActivity, "Selamat Datang $userName", Toast.LENGTH_SHORT).show()
    }
}