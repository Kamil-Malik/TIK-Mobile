package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mobiletik.model.data.TemplateUser
import com.example.mobiletik.model.data.UserInfo
import com.example.mobiletik.presentation.view.LoginActivity
import com.example.mobiletik.presentation.view.MainActivity
import com.example.mobiletik.utility.Loading
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Authentication {

    private val auth = Firebase.auth

    fun checkUser(mActivity : Activity) {
        if (auth.currentUser != null) {
            mActivity.startActivity(Intent(mActivity, MainActivity::class.java))
            mActivity.finish()
        }
    }

    fun getUID() : String {
        val uid = auth.currentUser!!.uid
        return uid
    }

    fun signIn(mActivity : Activity, email : String, password : String) {
        Log.d(TAG, "signIn: Signin...")
        val loading = Loading(mActivity)
        loading.startLoading()
        CoroutineScope(Dispatchers.IO).launch {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Log.d(TAG, "signIn: Sign In Berhasil")
                loading.dismissLoading()
                mActivity.startActivity(Intent(mActivity, MainActivity::class.java))
                mActivity.finish()
            }.addOnFailureListener {
                Log.w(TAG, "signIn: Login Gagal karena ${it.message.toString()}")
                loading.dismissLoading()
                when (it) {
                    is FirebaseAuthInvalidUserException -> Toast.makeText(
                        mActivity,
                        "User tidak ditemukan",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    is FirebaseNetworkException -> Toast.makeText(
                        mActivity,
                        "Silahkan periksa koneksi dan coba lagi",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    is FirebaseAuthInvalidCredentialsException -> Toast.makeText(
                        mActivity,
                        "Email atau Password yang anda masukkan salah",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun checkAlvailibility(
        mActivity : Activity,
        email : String,
        password : String,
        userInfo : UserInfo
    ) {
        val loading = Loading(mActivity)
        Log.d(TAG, "checkAlvailibility: Sistem memeriksa ketersediaan NIS")
        loading.startLoading()
        CoroutineScope(Dispatchers.IO).launch {
            Firebase.database.getReference("NISList").child(userInfo.nis).get()
                .addOnSuccessListener { snapshot ->
                    loading.dismissLoading()
                    if (snapshot.exists()) {
                        Log.d(TAG, "checkAlvailibility: Data ditemukan, Registrasi akan dihentikan")
                        Toast.makeText(
                            mActivity,
                            "NIS Telah digunakan, silahkan melakukan reset Password",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (!snapshot.exists()) {
                        Log.d(
                            TAG,
                            "checkAlvailibility: NIS dapat digunakan, Registrasi akan dilanjutkan"
                        )
                        signUp(mActivity, email, password, userInfo)
                    }
                }.addOnFailureListener { exception ->
                    loading.dismissLoading()
                    when (exception) {
                        is DatabaseException -> {
                            Log.e(TAG, "checkAlvailibility: ${exception.message}")
                        }
                        is FirebaseNetworkException -> {
                            Log.e(TAG, "checkAlvailibility: ${exception.message}")
                            Toast.makeText(
                                mActivity,
                                "Silahkan periksa koneksi anda dan coba lagi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }

    private fun signUp(
        mActivity : Activity,
        email : String,
        password : String,
        userInfo : UserInfo
    ) {
        val loading = Loading(mActivity)
        loading.startLoading()
        CoroutineScope(Dispatchers.IO).launch {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Log.d(TAG, "signUp: Akun berhasil dibuat")
                val userData = TemplateUser(
                    getUID(),
                    userInfo.nama,
                    userInfo.nis,
                    userInfo.email
                )
                Firebase.database.getReference("NISList").child(userInfo.nis).setValue("Registered")
                Firebase.firestore.collection("Users").document(getUID()).set(userData)
                    .addOnSuccessListener {
                        loading.dismissLoading()
                        mActivity.startActivity(Intent(mActivity, MainActivity::class.java))
                        mActivity.finish()
                    }
            }.addOnFailureListener { error ->
                CoroutineScope(Dispatchers.Main).launch {
                    Log.w(TAG, "signUp: ${error.message}")
                    loading.dismissLoading()
                    when (error) {
                        is FirebaseAuthWeakPasswordException -> Toast.makeText(
                            mActivity,
                            "Silahkan masukkan Password yang lebih baik",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        is FirebaseAuthEmailException -> Toast.makeText(
                            mActivity,
                            "Email telah terdaftar, silahkan Login ke akun tersebut",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        is FirebaseNetworkException -> Toast.makeText(
                            mActivity,
                            "Silahkan periksa koneksi anda dan coba lagi",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }


    fun resetPassword(mActivity : Activity, email : String) {
        val loading = Loading(mActivity)
        loading.startLoading()
        CoroutineScope(Dispatchers.IO).launch {
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                Log.d(TAG, "resetPassword: Link reset Password berhasil dikirimkan")
                loading.dismissLoading()
                Toast.makeText(
                    mActivity,
                    "Email permintaan reset password berhasil dikirimkan, silahkan periksa email anda",
                    Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener {
                loading.dismissLoading()
                Log.w(TAG, "resetPassword: Link reset Password Gagal Dikirimkan")
                when (it) {
                    is FirebaseNetworkException -> Toast.makeText(
                        mActivity,
                        "Silahkan periksa koneksi anda dan coba lagi",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    is FirebaseAuthEmailException -> Toast.makeText(
                        mActivity,
                        "Email tidak terdaftar, silahkan Daftar terlebih dahulu",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    fun signOut(mActivity : Activity) {
        auth.signOut()
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        try {
            sharedPref.edit().clear().apply()
        } catch (e : Exception) {
            Toast.makeText(mActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        mActivity.startActivity(Intent(mActivity, LoginActivity::class.java))
        mActivity.finish()
    }
}