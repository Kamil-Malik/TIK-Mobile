package com.example.mobiletik.presentation.view

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mobiletik.databinding.ActivityKuisBinding
import com.example.mobiletik.domain.usecase.ToastFunction
import com.example.mobiletik.domain.usecase.UpdateAttemptIntoFirestore
import com.example.mobiletik.domain.usecase.UploadScore.uploadQuizScoreIntoFirestore
import com.example.mobiletik.domain.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class KuisActivity : AppCompatActivity() {

    internal lateinit var binding: ActivityKuisBinding
    var penjelasan: String = ""
    var correctAnswer: String = ""
    var answer = ""
    var score = 0
    var indexQuestion = 1

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.tvPenjelasan.isVisible = false
        val quizTitle = intent.getStringExtra("nomor") as String
        val loading = Loading(this)
        loading.startLoading()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val title =
                    Firebase.database.getReference("Kuis").child(quizTitle).child("judul").get()
                        .await()
                withContext(Dispatchers.Main) {
                    binding.judulKuis.text = title.value.toString()
                }
            }
            withContext(Dispatchers.Main) {
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
                if (btnNext.text == "Periksa Jawaban") {
                    tvPenjelasan.text = penjelasan
                    tvPenjelasan.isVisible = true
                    if (indexQuestion == 5) {
                        btnNext.text = "Selesai"
                    } else {
                        btnNext.text = "Selanjutnya"
                    }
                    if (binding.jawabanUser.text == correctAnswer) {
                        score += 20
                        indexQuestion += 1
                        binding.jawabanUser.text = ""
                        ToastFunction.toastShort(
                            this@KuisActivity,
                            "Jawaban Benar, skor sekarang $score"
                        )
                    } else {
                        indexQuestion += 1
                        binding.jawabanUser.text = ""
                        ToastFunction.toastShort(
                            this@KuisActivity,
                            "Jawaban Salah, skor sekarang $score"
                        )
                    }
                } else if (btnNext.text == "Selanjutnya") {
                    tvPenjelasan.isVisible = false
                    tvPenjelasan.text = ""
                    btnNext.text = "Periksa Jawaban"
                    getQuestion(quizTitle, indexQuestion)
                } else if (btnNext.text == "Selesai") {
                    toastScore(score)
                    updateScoreInSharedPreferences(quizTitle, score)
                    updateAttemptIntoSharedPref(quizTitle)
                    uploadQuizScoreIntoFirestore(quizTitle, score)
                    updateAttemptIntoFirestore(quizTitle)
                }
            }
        }
    }

    private fun updateAttemptIntoFirestore(quizTitle: String) {
        val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1) + "Attempt"
        val currentAttempt =
            getSharedPreferences("userProfile", Context.MODE_PRIVATE).getLong(newIndex, 0)
        UpdateAttemptIntoFirestore.updateAttemptIntoFirestore(quizTitle, currentAttempt)
    }

    private fun updateAttemptIntoSharedPref(quizTitle: String) {
        val sharedPref = getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1) + "Attempt"
        val attempt = sharedPref.getLong(newIndex, 0)
        sharedPref.edit().putLong(newIndex, attempt + 1).apply()
    }

    @DelicateCoroutinesApi
    private fun getQuestion(quizTitle: String, indexQuestion: Int) {
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
                    penjelasan = question.child("penjelasan").value.toString()
                    question.child("kunci").value.toString().also { correctAnswer = it }
                }
            }
        }
    }

    private fun updateScoreInSharedPreferences(quizTitle: String, score: Int) {
        val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1)
        getSharedPreferences("userProfile", Context.MODE_PRIVATE).edit()
            .putLong(newIndex, score.toLong()).apply()
        finish()
    }

    @DelicateCoroutinesApi
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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