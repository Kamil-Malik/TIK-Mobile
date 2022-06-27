package com.example.mobiletik.viewmodel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobiletik.model.Authentication
import com.example.mobiletik.model.Database
import com.example.mobiletik.utility.Loading
import com.example.mobiletik.view.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivityViewmodel : ViewModel() {
    fun loadProfile(mActivity : Activity) {
        val database = Database
        database.checkUser(mActivity)
    }
}