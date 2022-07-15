package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.mobiletik.domain.utility.Loading
import com.example.mobiletik.presentation.view.MainActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object Login {

    private const val invalidUserException = "User tidak ditemukan"
    private const val networkException = "Silahkan periksa koneksi dan coba lagi"
    private const val invalidCredentialsException = "Email atau Password yang anda masukkan salah"

    fun login(mActivity : Activity, email : String, password : String) {
        val loading = Loading(mActivity)
        val handler = CoroutineExceptionHandler { _, exception ->
            CoroutineScope(Dispatchers.Main).launch {
                loading.dismissLoading()
                when (exception) {
                    is FirebaseAuthInvalidUserException -> toastError(mActivity,invalidUserException)
                    is FirebaseNetworkException -> toastError(mActivity, networkException)
                    is FirebaseAuthInvalidCredentialsException -> toastError(mActivity,invalidCredentialsException)
                    else -> toastError(mActivity, exception.message.toString())
                }
            }
        }
        loading.startLoading()
        CoroutineScope(Dispatchers.IO + handler).launch {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
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