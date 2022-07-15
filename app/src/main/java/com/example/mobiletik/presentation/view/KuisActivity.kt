package com.example.mobiletik.presentation.view

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobiletik.databinding.ActivityKuisBinding
import com.example.mobiletik.domain.usecase.GetQuestionTitle.getTitle
import com.example.mobiletik.domain.usecase.UploadScore.uploadQuizScoreIntoFirestore
import com.example.mobiletik.domain.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class KuisActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityKuisBinding
    var correctAnswer : String = ""
    var answer = ""
    var score = 0
    var indexQuestion = 1

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val quizTitle = intent.getStringExtra("nomor") as String
        val loading = Loading(this)
        loading.startLoading()
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                getTitle(quizTitle)
                getQuestion(quizTitle, indexQuestion)
            }
        }
        loading.dismissLoading()
        with(binding) {
            jawabanUser.text = answer
//            BTN A
            opsiA.setOnClickListener {
                jawabanUser.text = opsiA.text
            }
//            BTN B
            opsiB.setOnClickListener {
                jawabanUser.text = opsiB.text
            }
//            BTN C
            opsiC.setOnClickListener {
                jawabanUser.text = opsiC.text
            }
//            BTN D
            opsiD.setOnClickListener {
                jawabanUser.text = opsiD.text
            }
//            BTN Next
            btnNext.setOnClickListener {
                if (binding.jawabanUser.text == correctAnswer) {
                    score += 20
                    indexQuestion += 1
                    binding.jawabanUser.text = ""
                    Toast.makeText(
                        this@KuisActivity,
                        "Jawaban Benar, skor sekarang $score",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    getQuestion(quizTitle, indexQuestion)
                } else {
                    indexQuestion += 1
                    binding.jawabanUser.text = ""
                    Toast.makeText(
                        this@KuisActivity,
                        "Jawaban Salah, skor sekarang $score",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    getQuestion(quizTitle, indexQuestion)
                }
            }
        }
    }

    @DelicateCoroutinesApi
    fun scoreCheck() {
        if (score != 0) {
            val quizTitle = intent.getStringExtra("nomor") as String
            toastScore(score)
            uploadQuizScoreIntoFirestore(quizTitle, score)
            finish()
        } else {
            finish()
        }
    }

    @DelicateCoroutinesApi
    private fun getQuestion(quizTitle : String, indexQuestion : Int) {
        if (indexQuestion > 5) {
            toastScore(score)
            uploadQuizScoreIntoFirestore(quizTitle, score)
            updateScoreInSharedPreferences(quizTitle, score)
        }
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "getQuestion: $exception")
            CoroutineScope(Dispatchers.Main).launch {
                getQuestion(quizTitle, indexQuestion)
            }
        }
        CoroutineScope(Dispatchers.IO + handler).launch {
            val question = Firebase.database.getReference("Kuis").child(quizTitle)
                .child(indexQuestion.toString()).get().await()
            withContext(Dispatchers.Main + handler) {
                with(binding) {
                    nomorsoal.text = "Soal Nomor : $indexQuestion"
                    soal.text = question.child("soal").value.toString()
                    opsiA.text = question.child("opsiA").value.toString()
                    opsiB.text = question.child("opsiB").value.toString()
                    opsiC.text = question.child("opsiC").value.toString()
                    opsiD.text = question.child("opsiD").value.toString()
                    question.child("kunci").value.toString().also { correctAnswer = it }
                    if (indexQuestion == 5) {
                        btnNext.text = "Selesai"
                    }
                }
            }
        }
    }

    private fun updateScoreInSharedPreferences(quizTitle : String, score : Int) {
        val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1)
        getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
            .putLong(newIndex, score.toLong()).apply()
        finish()
    }

    @DelicateCoroutinesApi
    override fun onBackPressed() {
        scoreCheck()
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