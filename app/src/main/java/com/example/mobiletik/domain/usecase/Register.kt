package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.mobiletik.domain.utility.Loading
import com.example.mobiletik.presentation.view.MainActivity
import com.example.mobiletik.presentation.view.RegisterActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object Register {

    data class User(
        val uid : String,
        val userName : String,
        val userNIS : String,
        val userEmail : String,
        val kuisSatu : Long = 0,
        val kuisDua : Long = 0,
        val kuisTiga : Long = 0,
        val kuisEmpat : Long = 0,
        val kuisLima : Long = 0
    )

    private const val authWeakPasswordException = "Silahkan masukkan Password yang lebih baik"
    private const val authEmailException = "Email telah terdaftar, silahkan Login ke akun tersebut"
    private const val networkException = "Silahkan periksa koneksi anda dan coba lagi"

    fun signUpWithEmailAndPassword(
        mActivity : RegisterActivity,
        email : String,
        password : String,
        userName : String,
        userNIS : String
    ) {
        val loading = Loading(mActivity)
        loading.startLoading()
        val handler = CoroutineExceptionHandler { _, exception ->
            CoroutineScope(Dispatchers.Main).launch {
                loading.dismissLoading()
                when (exception) {
                    is FirebaseAuthWeakPasswordException -> toastError(mActivity, authWeakPasswordException)
                    is FirebaseAuthEmailException -> toastError(mActivity, authEmailException)
                    is FirebaseNetworkException -> toastError(mActivity, networkException)
                    else -> toastError(mActivity, exception.message.toString())
                }
            }
        }
        CoroutineScope(Dispatchers.IO + handler).launch {
            Firebase.auth.createUserWithEmailAndPassword(email,password).await()
            val uid = Authentication.getUID()
            val userData = User(uid, userName, userNIS, email)
            Firebase.firestore.collection("Users").document(uid).set(userData).await()
            withContext(Dispatchers.Main) {
                loading.dismissLoading()
                Intent(mActivity, MainActivity::class.java).also {
                    mActivity.startActivity(it)
                    mActivity.finish()
                }
            }
        }
    }

    private fun toastError(mActivity : Activity, error : String) {
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show()
    }
}