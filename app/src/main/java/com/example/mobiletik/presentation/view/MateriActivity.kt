package com.example.mobiletik.presentation.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.R
import com.example.mobiletik.databinding.ActivityMateriBinding
import com.github.barteksc.pdfviewer.util.FitPolicy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MateriActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMateriBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val indeks = intent.getIntExtra("bab", 0)
        val judulMateri = resources.getStringArray(R.array.judulMateri)
        val namaFIle = resources.getStringArray(R.array.namaFile)
        binding.toolbar.title = judulMateri[indeks]
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "onCreate: ${namaFIle[indeks]}.pdf")
            binding.pdfView.fromAsset("${namaFIle[indeks]}.pdf")
                .pageFitPolicy(FitPolicy.BOTH)
                .autoSpacing(true)
                .fitEachPage(true)
                .pageSnap(true)
                .load()
        }



        binding.fabBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}