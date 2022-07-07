package com.example.mobiletik.presentation.view

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletik.databinding.ActivityKuisBinding
import com.example.mobiletik.model.usecase.DatabaseUser
import com.example.mobiletik.presentation.viewmodel.KuisActivityViewmodel
import com.example.mobiletik.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KuisActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityKuisBinding
    var correctAnswer = ""
    var answer = ""
    var score = 0
    var indexQuestion = 1

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val quizTitle = intent.getStringExtra("nomor").toString()
        val loading = Loading(this)
        loading.startLoading()
        val sharedPref = getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        if (sharedPref.getString(quizTitle, "-") != "-") {
            Toast.makeText(this, "Anda telah mengerjakan kuis ini", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Selamat Mengerjakan", Toast.LENGTH_SHORT).show()
        }
        Firebase.database.getReference("Kuis").child(quizTitle).child("judul").get()
            .addOnSuccessListener { snapshot ->
                binding.judulKuis.text = snapshot.value.toString()
                loading.dismissLoading()
            }
        val model = ViewModelProvider(this)[KuisActivityViewmodel::class.java]
        model.getQuestion(this, quizTitle, indexQuestion)
        binding.jawabanUser.text = answer

        binding.opsiA.setOnClickListener {
            binding.jawabanUser.text = binding.opsiA.text.toString()
        }
        binding.opsiB.setOnClickListener {
            binding.jawabanUser.text = binding.opsiB.text.toString()
        }
        binding.opsiC.setOnClickListener {
            binding.jawabanUser.text = binding.opsiC.text.toString()
        }
        binding.opsiD.setOnClickListener {
            binding.jawabanUser.text = binding.opsiD.text.toString()
        }
        binding.btnNext.setOnClickListener {
            answer = binding.jawabanUser.text.toString()
            if (answer == correctAnswer) {
                score += 20
                indexQuestion += 1
                binding.jawabanUser.text = ""
                Toast.makeText(this, "Jawaban Benar, skor sekarang $score", Toast.LENGTH_SHORT)
                    .show()
                switch(this, quizTitle, indexQuestion, model, score)
            } else {
                indexQuestion += 1
                binding.jawabanUser.text = ""
                Toast.makeText(this, "Jawaban Salah, skor sekarang $score", Toast.LENGTH_SHORT)
                    .show()
                switch(this, quizTitle, indexQuestion, model, score)
            }
        }

    }

    override fun onBackPressed() {
        val scoreAkhir = score
        if (scoreAkhir != 0) {
            val quizTitle = intent.getStringExtra("nomor").toString()
            DatabaseUser.uploadScore(this, quizTitle, scoreAkhir)
            toastScore(scoreAkhir)
            finish()
        } else {
            finish()
        }
    }

    private fun switch(
        mActivity: KuisActivity,
        context: String,
        index: Int,
        model: KuisActivityViewmodel,
        score: Int
    ) {
        if (this.indexQuestion <= 5) {
            model.getQuestion(mActivity, context, index)
        } else if (this.indexQuestion > 5) {
            toastScore(score)
            DatabaseUser.uploadScore(this, context, score)
        }
    }

    fun startTimer() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(time: Long) {
                binding.timer.text = "Sisa Waktu : ${time.toInt() / 1000} Detik"
            }

            override fun onFinish() {
                binding.btnNext.callOnClick()
            }
        }
        timer.start()
    }

    private fun toastScore(score: Int) {
        if (score >= 80) {
            Toast.makeText(
                this,
                "Selamat anda mendapatkan nilai yang sangat baik",
                Toast.LENGTH_SHORT
            )
                .show()
        } else if (score in 40..80) {
            Toast.makeText(
                this,
                "Selamat anda berhasil mendapatkan nilai $score, Namun silahkan ulas kembali materi yang dipelajari",
                Toast.LENGTH_LONG
            ).show()
        } else if (score < 40) {
            Toast.makeText(
                this,
                "Silahkan meminta penjelasan kembali kepada pengajar",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}