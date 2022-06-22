package com.example.mobiletik.viewmodel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobiletik.model.Authentication
import com.example.mobiletik.utility.Loading
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivityViewmodel : ViewModel() {

    internal lateinit var userName : String
    internal lateinit var userNis : String
    internal lateinit var userEmail : String

    fun loadProfile(mActivity : Activity) {
        val database = Firebase.database
        database.setPersistenceEnabled(true)
        val uid = Authentication.getUID()
        val ref = database.getReference("User").child(uid)
        val loading = Loading(mActivity)
        loading.startLoading()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                loading.dismissLoading()
                userName = snapshot.child("nama").value.toString()
                userNis = snapshot.child("nis").value.toString()
                userEmail = snapshot.child("email").value.toString()
                Toast.makeText(mActivity, "Selamat Datang $userName", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error : DatabaseError) {
                loading.dismissLoading()
                Toast.makeText(mActivity, "Proses dibatalkan", Toast.LENGTH_SHORT).show()
            }

        })
    }
}