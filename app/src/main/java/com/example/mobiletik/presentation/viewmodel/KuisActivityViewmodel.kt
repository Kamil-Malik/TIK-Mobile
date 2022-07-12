package com.example.mobiletik.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobiletik.presentation.view.KuisActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class KuisActivityViewmodel : ViewModel() {

    val timer : MutableLiveData<Int> = MutableLiveData(60)

    fun getQuestion(mActivity : KuisActivity, quizTitle : String, indexQuestion : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val question = Firebase.database.getReference("Kuis").child(quizTitle)
                .child(indexQuestion.toString()).get().await()
            withContext(Dispatchers.Main) {
                with(mActivity) {
                    with(binding) {
                        nomorsoal.text = "Soal Nomor : $indexQuestion"
                        soal.text = question.child("soal").value.toString()
                        opsiA.text = question.child("opsiA").value.toString()
                        opsiB.text = question.child("opsiB").value.toString()
                        opsiC.text = question.child("opsiC").value.toString()
                        opsiD.text = question.child("opsiD").value.toString()
                    }
                    if (indexQuestion == 5) {
                        binding.btnNext.text = "Selesai"
                    }
                    timer.value = 60
                    correctAnswer = question.child("kunci").value.toString()
                }
            }
        }
    }


}