package com.example.mobiletik.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobiletik.R
import com.example.mobiletik.databinding.ActivityMateriBinding

class MateriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMateriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val indeks = intent.getIntExtra("bab", 0)
        val judulMateri = resources.getStringArray(R.array.judulMateri)
        val urlMateri = resources.getStringArray(R.array.urlMateri)
        binding.toolbar.title = judulMateri[indeks]
        binding.webView.loadUrl(urlMateri[indeks])


        binding.fabBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}