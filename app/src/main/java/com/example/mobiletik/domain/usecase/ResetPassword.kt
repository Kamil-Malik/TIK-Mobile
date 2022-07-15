package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.mobiletik.domain.utility.Loading
import com.example.mobiletik.presentation.view.LoginActivity
import com.example.mobiletik.presentation.view.ResetPasswordActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object ResetPassword {

    private const val networkException = "Silahkan periksa koneksi dan coba lagi"
    private const val authEmailException = "Email tidak terdaftar, silahkan Daftar terlebih dahulu"

    fun sendResetPasswordLink(mActivity : ResetPasswordActivity, email : String) {
        val loading = Loading(mActivity)
        val handler = CoroutineExceptionHandler { _, exception ->
            CoroutineScope(Dispatchers.Main).launch {
                loading.dismissLoading()
                when (exception) {
                    is FirebaseAuthEmailException -> toastError(mActivity, authEmailException)
                    is FirebaseNetworkException -> toastError(mActivity, networkException)
                    else -> toastError(mActivity, exception.message.toString())
                }
            }
        }
        loading.startLoading()
        CoroutineScope(Dispatchers.IO + handler).launch {
            Firebase.auth.sendPasswordResetEmail(email).await()
            withContext(Dispatchers.Main) {
                loading.dismissLoading()
                Intent(mActivity, LoginActivity::class.java).also {
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