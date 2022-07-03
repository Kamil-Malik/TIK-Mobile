package com.example.mobiletik.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.mobiletik.utility.Loading
import com.example.mobiletik.view.main.MainActivity
import com.example.mobiletik.view.auth.LoginActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

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
        val loading = Loading(mActivity)
        loading.startLoading()
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            loading.dismissLoading()
            mActivity.startActivity(Intent(mActivity, MainActivity::class.java))
            mActivity.finish()
        }.addOnFailureListener {
            loading.dismissLoading()
            when (it) {
                is FirebaseAuthInvalidUserException -> Toast.makeText(mActivity,
                    "User tidak ditemukan",
                    Toast.LENGTH_SHORT)
                    .show()
                is FirebaseNetworkException -> Toast.makeText(mActivity,
                    "Silahkan periksa koneksi dan coba lagi",
                    Toast.LENGTH_LONG)
                    .show()
                is FirebaseAuthInvalidCredentialsException -> Toast.makeText(mActivity,
                    "Email atau Password yang anda masukkan salah",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUp(mActivity : Activity, email : String, password : String, userInfo : UserInfo) {
        val loading = Loading(mActivity)
        loading.startLoading()
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val database = Firebase.database
            val ref = database.getReference("User").child(getUID()).child("Profile")
            ref.setValue(userInfo).addOnSuccessListener {
                loading.dismissLoading()
                Toast.makeText(mActivity,
                    "Registrasi berhasil, selamat datang ${userInfo.nama}",
                    Toast.LENGTH_LONG).show()
                mActivity.startActivity(Intent(mActivity, MainActivity::class.java))
                mActivity.finish()
            }

        }.addOnFailureListener {
            loading.dismissLoading()
            when (it) {
                is FirebaseAuthWeakPasswordException -> Toast.makeText(mActivity,
                    "Silahkan masukkan Password yang lebih baik",
                    Toast.LENGTH_SHORT)
                    .show()
                is FirebaseAuthEmailException -> Toast.makeText(mActivity,
                    "Email telah terdaftar, silahkan Login ke akun tersebut",
                    Toast.LENGTH_LONG)
                    .show()
                is FirebaseNetworkException -> Toast.makeText(mActivity,
                    "Silahkan periksa koneksi anda dan coba lagi",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun resetPassword(mActivity : Activity, email : String) {
        val loading = Loading(mActivity)
        loading.startLoading()
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            loading.dismissLoading()
            Toast.makeText(
                mActivity,
                "Email permintaan reset password berhasil dikirimkan, silahkan periksa email anda",
                Toast.LENGTH_LONG
            ).show()
        }.addOnFailureListener {
            loading.dismissLoading()
            when (it) {
                is FirebaseNetworkException -> Toast.makeText(mActivity,
                    "Silahkan periksa koneksi anda dan coba lagi",
                    Toast.LENGTH_LONG)
                    .show()
                is FirebaseAuthEmailException -> Toast.makeText(mActivity,
                    "Email tidak terdaftar, silahkan Daftar terlebih dahulu",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun signOut(mActivity : Activity) {
        auth.signOut()
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        try {
            sharedPref.edit().clear().apply()
        }catch (e: Exception){
            Toast.makeText(mActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        mActivity.startActivity(Intent(mActivity, LoginActivity::class.java))
        mActivity.finish()
    }

}