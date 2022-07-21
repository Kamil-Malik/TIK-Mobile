package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mobiletik.domain.usecase.GetUID.getUID
import com.example.mobiletik.domain.utility.Loading
import com.example.mobiletik.model.data.QuizAttempt
import com.example.mobiletik.model.data.QuizResult
import com.example.mobiletik.model.data.TemplateUser
import com.example.mobiletik.model.data.userData
import com.example.mobiletik.presentation.view.LoginActivity
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

    private const val authWeakPasswordException = "Silahkan masukkan Password yang lebih baik"
    private const val authEmailException = "Email telah terdaftar, silahkan Login ke akun tersebut"
    private const val networkException = "Silahkan periksa koneksi anda dan coba lagi"

    @OptIn(DelicateCoroutinesApi::class)
    fun signUpWithEmailAndPassword(
        mActivity: RegisterActivity,
        email: String,
        password: String,
        userName: String,
        userNIS: String,
        userKelas: String
    ) {
        val loading = Loading(mActivity)
        loading.startLoading()
        val handler = CoroutineExceptionHandler { _, exception ->
            CoroutineScope(Dispatchers.Main).launch {
                loading.dismissLoading()
                Log.w(TAG, "signUpWithEmailAndPassword: ${exception.message}")
                when (exception) {
                    is FirebaseAuthWeakPasswordException -> toastError(
                        mActivity,
                        authWeakPasswordException
                    )
                    is FirebaseAuthEmailException -> toastError(mActivity, authEmailException)
                    is FirebaseNetworkException -> toastError(mActivity, networkException)
                    else -> toastError(mActivity, exception.message.toString())
                }
            }
        }

        CoroutineScope(Dispatchers.IO + handler).launch {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            val uid = getUID()

            //Break into 3 Jobs
            val user = userData(
                uid,
                userName,
                userNIS,
                email,
                userKelas
            )
            val defaultQuiz = QuizResult()
            val defaultAttempt = QuizAttempt()

            Firebase.firestore.collection("Users").document(uid).set(user).await()
            Firebase.firestore.collection("Users").document(uid).set(defaultQuiz).await()
            Firebase.firestore.collection("Users").document(uid).set(defaultAttempt).await()
            Log.d(TAG, "signUpWithEmailAndPassword: $user")

            withContext(Dispatchers.Main) {
                loading.dismissLoading()
                Intent(mActivity, LoginActivity::class.java).also {
                    mActivity.startActivity(it)
                    mActivity.finish()
                }
            }
        }
    }

    private fun toastError(mActivity: Activity, error: String) {
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show()
    }
}