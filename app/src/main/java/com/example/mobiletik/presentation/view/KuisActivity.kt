package com.example.mobiletik.presentation.view

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletik.databinding.ActivityKuisBinding
import com.example.mobiletik.domain.usecase.Firestore.updateQuizScoreInFirestore
import com.example.mobiletik.presentation.viewmodel.KuisActivityViewmodel
import com.example.mobiletik.model.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KuisActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityKuisBinding
    var correctAnswer : String = ""
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
        Firebase.database.getReference("Kuis").child(quizTitle).child("judul").get()
            .addOnSuccessListener { snapshot ->
                binding.judulKuis.text = snapshot.value.toString()
                loading.dismissLoading()
            }
        val model = ViewModelProvider(this)[KuisActivityViewmodel::class.java]
        model.getQuestion(this, quizTitle, indexQuestion)

        with(binding) {
            jawabanUser.text = answer
            opsiA.setOnClickListener {
                jawabanUser.text = opsiA.text
            }
            opsiB.setOnClickListener {
                jawabanUser.text = opsiB.text
            }
            opsiC.setOnClickListener {
                jawabanUser.text = opsiC.text
            }
            opsiD.setOnClickListener {
                jawabanUser.text = opsiD.text
            }
        }
        binding.btnNext.setOnClickListener {
            if (binding.jawabanUser.text == correctAnswer) {
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

    fun safety() {
        val scoreAkhir = score
        if (scoreAkhir != 0) {
            val quizTitle = intent.getStringExtra("nomor").toString()
            updateQuizScoreInFirestore(this, quizTitle, scoreAkhir)
            toastScore(scoreAkhir)
            finish()
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        safety()
    }

    private fun switch(
        mActivity : KuisActivity,
        context : String,
        index : Int,
        model : KuisActivityViewmodel,
        score : Int
    ) {
        if(indexQuestion<=5){
            model.getQuestion(mActivity, context, index)
        }
        else{
            toastScore(score)
            updateQuizScoreInFirestore(this, context, score)
        }
    }

    fun startTimer() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(time : Long) {
                binding.timer.text = "Sisa Waktu : ${time.toInt() / 1000} Detik"
            }

            override fun onFinish() {
                binding.btnNext.callOnClick()
            }
        }
        timer.cancel()
        timer.start()
    }

    private fun toastScore(score : Int) {
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