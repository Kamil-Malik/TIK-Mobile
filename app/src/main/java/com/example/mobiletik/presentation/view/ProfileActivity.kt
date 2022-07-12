package com.example.mobiletik.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobiletik.R
import com.example.mobiletik.databinding.ActivityProfileBinding
import com.example.mobiletik.domain.usecase.UserData.getUserDataFromSharedpref
import com.google.firebase.firestore.core.ActivityScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val data = getUserDataFromSharedpref(this)
        val finalScore = (data.kuisSatu + data.kuisDua + data.kuisTiga + data.kuisEmpat + data.kuisLima) / 5
        val textJudulNilai = resources.getStringArray(R.array.judulKuis)
        lifecycleScope.launch(Dispatchers.Main){
            with(binding){
                tvNama.text = data.userName
                tvNis.text = data.userNIS
                tvEmail.text = data.userEmail
                tvHasilKuisSatu.text = data.kuisSatu.toString()
                tvHasilKuisDua.text = data.kuisDua.toString()
                tvHasilKuisTiga.text = data.kuisTiga.toString()
                tvHasilKuisEmpat.text = data.kuisEmpat.toString()
                tvHasilKuisLima.text = data.kuisLima.toString()
                tvHasilKuisFinal.text = finalScore.toInt().toString()
                tvKuis1.text = textJudulNilai[0]
                tvKuis2.text = textJudulNilai[1]
                tvKuis3.text = textJudulNilai[2]
                tvKuis4.text = textJudulNilai[3]
                tvKuis5.text = textJudulNilai[4]
            }
        }
        binding.fabBack.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}