package com.example.mobiletik.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletik.databinding.ActivityLicenseBinding
import com.example.mobiletik.model.data.LicenseData
import com.example.mobiletik.presentation.adapter.LicenseAdapter

class LicenseActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLicenseBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val licenseLIst = mutableListOf(
            LicenseData("Android KTX", "https://developer.android.com/kotlin/ktx"),
            LicenseData(
                "Androidx Appcompact",
                "https://developer.android.com/reference/androidx/appcompat/app/package-summary?hl=id"
            ),
            LicenseData(
                "Androidx ConstraintLayout",
                "https://developer.android.com/training/constraint-layout?hl=id"
            ),
            LicenseData(
                "Androidx Legacy Support",
                "https://developer.android.com/jetpack/androidx/releases/legacy?hl=id"
            ),
            LicenseData(
                "Android Firebase Database Ktx",
                "https://firebase.google.com/docs/reference/kotlin/com/google/firebase/database/ktx/package-summary"
            ),
            LicenseData(
                "Android Firebase Realtime Database Ktx",
                "https://firebase.google.com/docs/reference/kotlin/com/google/firebase/database/ktx/package-summary"
            ),
            LicenseData(
                "Circle ImageView",
                "https://github.com/hdodenhof/CircleImageView",
                "Copyright 2014 - 2020 Henning Dodenhof",
                "Apache 2.0"
            ),
            LicenseData(
                "Androidx Recyclerview",
                "android-RecyclerView/LICENSE at master · googlearchive/android-RecyclerView (github.com)"
            ),
            LicenseData(
                "SDP - a scalable size unit",
                "https://github.com/intuit/sdp",
                "Copyright (c) 2013 - 2015 Intuit Inc."
            )
        )

        val adapter = LicenseAdapter(licenseLIst)
        binding.rvLicense.adapter = adapter
        binding.rvLicense.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}