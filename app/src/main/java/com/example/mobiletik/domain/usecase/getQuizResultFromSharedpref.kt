package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Context
import com.example.mobiletik.model.data.ScoreKuis

object getQuizResultFromSharedpref {

    fun quizResult(mActivity : Activity) : ScoreKuis {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val kuis1 = sharedPref.getLong("kuisSatu", 0)
        val kuis2 = sharedPref.getLong("kuisDua", 0)
        val kuis3 = sharedPref.getLong("kuisTiga", 0)
        val kuis4 = sharedPref.getLong("kuisEmpat", 0)
        val kuis5 = sharedPref.getLong("kuisLima", 0)
        return ScoreKuis(kuis1, kuis2, kuis3, kuis4, kuis5)
    }
}