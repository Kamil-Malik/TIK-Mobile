package com.example.mobiletik.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobiletik.view.main.KuisActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KuisActivityViewmodel : ViewModel() {

    fun getQuestion(mActivity : KuisActivity, quizTitle : String, indexQuestion : Int) {
        Firebase.database.getReference("Kuis").child(quizTitle).child(indexQuestion.toString()).get()
            .addOnSuccessListener {
                mActivity.correctAnswer = it.child("kunci").value.toString()
                mActivity.binding.soal.text = it.child("soal").value.toString()
                mActivity.binding.opsiA.text = it.child("opsiA").value.toString()
                mActivity.binding.opsiB.text = it.child("opsiB").value.toString()
                mActivity.binding.opsiC.text = it.child("opsiC").value.toString()
                mActivity.binding.opsiD.text = it.child("opsiD").value.toString()
            }.addOnFailureListener {
                Toast.makeText(mActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }


}