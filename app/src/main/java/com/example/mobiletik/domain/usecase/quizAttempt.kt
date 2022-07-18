package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.Context

object quizAttempt {
    data class quizAttemptHistory(
        val kuisSatuAttempt: Long,
        val kuisDuaAttempt: Long,
        val kuisTigaAttempt: Long,
        val kuisEmpatAttempt: Long,
        val kuisLimaAttempt: Long
    )

    fun quizAttempt(mActivity: Activity) : quizAttemptHistory {
        val sharedPref = mActivity.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val kuis1 = sharedPref.getLong("kuisSatuAttempt", 0)
        val kuis2 = sharedPref.getLong("kuisDuaAttempt", 0)
        val kuis3 = sharedPref.getLong("kuisTigaAttempt", 0)
        val kuis4 = sharedPref.getLong("kuisEmpatAttempt", 0)
        val kuis5 = sharedPref.getLong("kuisLimaAttempt", 0)
        return quizAttemptHistory(kuis1, kuis2, kuis3, kuis4, kuis5)
    }
}