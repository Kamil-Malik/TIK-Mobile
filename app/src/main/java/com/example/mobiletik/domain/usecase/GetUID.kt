package com.example.mobiletik.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object GetUID {

    fun getUID(): String {
        val uid = Firebase.auth.currentUser!!.uid
        return uid
    }
}