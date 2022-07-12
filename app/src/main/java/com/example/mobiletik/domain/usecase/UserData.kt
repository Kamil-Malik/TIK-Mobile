package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Context
import com.example.mobiletik.model.data.ScoreKuis
import com.example.mobiletik.model.data.TemplateUser

object UserData {

    fun getUserDataFromSharedpref(mActivity : Activity) : TemplateUser {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        with(sharedPref) {
            val userName = getString("userName", "")!!
            val userNis = getString("userNIS", "")!!
            val userEmail = getString("userEmail", "")!!
            val kuis1 = getLong("kuisSatu", 0)
            val kuis2 = getLong("kuisDua", 0)
            val kuis3 = getLong("kuisTiga", 0)
            val kuis4 = getLong("kuisEmpat", 0)
            val kuis5 = getLong("kuisLima", 0)
            return TemplateUser(
                Authentication.getUID(),
                userName,
                userNis,
                userEmail,
                kuis1,
                kuis2,
                kuis3,
                kuis4,
                kuis5
            )
        }
    }

    fun saveQuizResultIntoSharedPref(mActivity : Activity, index : String, score : Int) {
        mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
            .putLong(index, score.toLong()).apply()
    }

    fun getQuizResultFromSharedpref(mActivity : Activity) : ScoreKuis {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val kuis1 = sharedPref.getLong("kuisSatu", 0)
        val kuis2 = sharedPref.getLong("kuisDua", 0)
        val kuis3 = sharedPref.getLong("kuisTiga", 0)
        val kuis4 = sharedPref.getLong("kuisEmpat", 0)
        val kuis5 = sharedPref.getLong("kuisLima", 0)
        return ScoreKuis(kuis1, kuis2, kuis3, kuis4, kuis5)

    }
}