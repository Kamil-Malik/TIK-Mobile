package com.example.mobiletik.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.example.mobiletik.R

class Loading(val mActivity : Activity) {
    private lateinit var isdialog : AlertDialog

    @SuppressLint("InflateParams")
    fun startLoading() {
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.layer_loading, null)
        val builder = AlertDialog.Builder(mActivity)
        with(builder) {
            setView(dialogView)
            setCancelable(false)
            isdialog = create()
        }
        isdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isdialog.show()
    }

    fun dismissLoading() {
        isdialog.dismiss()
    }
}