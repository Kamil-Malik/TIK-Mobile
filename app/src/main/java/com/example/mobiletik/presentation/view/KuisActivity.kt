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
import com.example.mobiletik.domain.usecase.GetUID.getUID
import com.example.mobiletik.domain.usecase.ToastFunction
import com.example.mobiletik.domain.usecase.UpdateAttemptIntoFirestore
import com.example.mobiletik.domain.utility.Loading
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
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
        Log.d(TAG, "onCreate: Penjelasan ditutup")

        val quizTitle = intent.getStringExtra("nomor") as String
        Log.d(TAG, "onCreate: Judul Kuis adalah $quizTitle")

        val loading = Loading(this)
        loading.startLoading()
        Log.d(TAG, "onCreate: Loading dimulai")

        lifecycleScope.launch {
            val handler = CoroutineExceptionHandler { _, _ ->
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        this@KuisActivity,
                        "Silahkan periksa koneksi terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
            withContext(Dispatchers.IO + handler) {
                val startingPoint =
                    Firebase.database.getReference("Kuis").child(quizTitle).get().await()
                Log.d(TAG, "onCreate: Data didapatkan. $startingPoint")

                val judul = startingPoint.child("judul").value as String
                val soal = startingPoint.child("1").child("soal").value as String
                val opsiA = startingPoint.child("1").child("opsiA").value as String
                val opsiB = startingPoint.child("1").child("opsiB").value as String
                val opsiC = startingPoint.child("1").child("opsiC").value as String
                val opsiD = startingPoint.child("1").child("opsiD").value as String
                val penjelasan = startingPoint.child("1").child("penjelasan").value as String
                val kunci = startingPoint.child("1").child("kunci").value as String

                withContext(Dispatchers.Main) {
                    binding.judulKuis.text = judul
                    binding.soal.text = soal
                    binding.nomorsoal.text = "Soal Nomor : $indexQuestion"
                    binding.opsiA.text = opsiA
                    binding.opsiB.text = opsiB
                    binding.opsiC.text = opsiC
                    binding.opsiD.text = opsiD
                    correctAnswer = kunci
                    this@KuisActivity.penjelasan = penjelasan
                    Log.d(TAG, "onCreate: Tampilan selesai dimuat")
                }
            }
        }

        loading.dismissLoading()
        Log.d(TAG, "onCreate: Loading selesai")


        binding.jawabanUser.text = answer
        Log.d(TAG, "onCreate: jawaban benar : $answer")

        binding.opsiA.setOnClickListener { //Btn Opsi A
            binding.jawabanUser.text = binding.opsiA.text
        }

        binding.opsiB.setOnClickListener { //Btn Opsi B
            binding.jawabanUser.text = binding.opsiB.text
        }

        binding.opsiC.setOnClickListener { //Btn Opsi C
            binding.jawabanUser.text = binding.opsiC.text
        }

        binding.opsiD.setOnClickListener { //Btn Opsi D
            binding.jawabanUser.text = binding.opsiD.text
        }

        binding.btnNext.setOnClickListener { //Btn Periksa Jawaban / Selanjutnya / Selesai
            if (binding.btnNext.text == "Periksa Jawaban") {
                binding.tvPenjelasan.text = penjelasan
                binding.tvPenjelasan.isVisible = true

                if (indexQuestion == 5) {
                    binding.btnNext.text = "Selesai"
                } else {
                    binding.btnNext.text = "Selanjutnya"
                }
                Log.d(TAG, "onCreate: Tulisan sekarang $binding.btnNext.text")

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

            } else if (binding.btnNext.text == "Selanjutnya") {
                binding.tvPenjelasan.isVisible = false
                binding.tvPenjelasan.text = ""
                binding.btnNext.text = "Periksa Jawaban"
                getQuestion(quizTitle, indexQuestion)
            } else if (binding.btnNext.text == "Selesai") {
                toastScore(score)
                uploadScoreAndUpdatePref(quizTitle, score)
            }
        }
    }

    private fun uploadScoreAndUpdatePref(quizTitle: String, score: Int) {
        val sharedPreferences = getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val newIndex = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1)
        val newIndexAttempt = quizTitle[0].lowercase() + quizTitle.removeRange(0, 1) + "Attempt"
        val newScore = score.toLong()

        //Start Loading
        val loading = Loading(this)
        loading.startLoading()

        //Update Attempt
        var currentAttempt =
            getSharedPreferences("userProfile", Context.MODE_PRIVATE).getLong(newIndexAttempt, 0)
        Log.d(TAG, "uploadScoreAndUpdatePref: $currentAttempt")
        with(sharedPreferences.edit()) {
            putLong(newIndexAttempt, currentAttempt + 1)
            apply()
        }
        currentAttempt =
            getSharedPreferences("userProfile", Context.MODE_PRIVATE).getLong(newIndexAttempt, 0)
        Log.d(TAG, "uploadScoreAndUpdatePref: $currentAttempt")

        //Update Pref
        var currentScore =
            getSharedPreferences("userProfile", Context.MODE_PRIVATE).getLong(newIndex, 0)

        Log.d(TAG, "uploadScoreAndUpdatePref: $currentScore")
        with(sharedPreferences.edit()) {
            putLong(newIndex, newScore)
            apply()
        }
        currentScore =
            getSharedPreferences("userProfile", Context.MODE_PRIVATE).getLong(newIndex, 0)
        Log.d(TAG, "uploadScoreAndUpdatePref: $currentScore")

        Log.d(TAG, "updateAttemptIntoFirestore: Jumlah percobaan $currentAttempt")

        UpdateAttemptIntoFirestore.updateAttemptIntoFirestore(quizTitle, currentAttempt)
    }


        //Upload Score
        lifecycleScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("Users").document(getUID()).update(newIndex, newScore)
                .await()
        }

        //Stop Loading
        loading.dismissLoading()
        finish()
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