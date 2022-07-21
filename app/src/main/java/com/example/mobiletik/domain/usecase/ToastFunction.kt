package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.widget.Toast

object ToastFunction {
    fun toastShort(mACtivity: Activity, msg: String) {
        Toast.makeText(mACtivity, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(mACtivity: Activity, msg: String) {
        Toast.makeText(mACtivity, msg, Toast.LENGTH_LONG).show()
    }
}