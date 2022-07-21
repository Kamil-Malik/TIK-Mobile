package com.example.mobiletik.presentation.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobiletik.R
import com.example.mobiletik.databinding.ActivityProfileBinding
import com.example.mobiletik.domain.usecase.GetQuizResultFromSharedPref
import com.example.mobiletik.domain.usecase.GetUID.getUID
import com.example.mobiletik.model.data.userData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        val getUser = getUser()
        val quizResult = GetQuizResultFromSharedPref.quizResult(this)
        setContentView(view)
        val finalScore =
            (quizResult.KuisSatu + quizResult.KuisDua + quizResult.KuisTiga + quizResult.KuisEmpat + quizResult.KuisLima) / 5
        if (finalScore < 60) {
            binding.tvHasilKuisFinal.setTextColor(R.color.red)
        } else {
            binding.tvHasilKuisFinal.setTextColor(R.color.dark_green)
        }
        val textJudulNilai = resources.getStringArray(R.array.judulKuis)
        Log.d(TAG, "onCreate: $getUser")
        lifecycleScope.launch(Dispatchers.Main) {
            with(binding) {
                tvNama.text = getUser.userName
                tvNis.text = getUser.userNIS
                tvEmail.text = getUser.userEmail
                tvKelas.text = getUser.userKelas
                tvHasilKuisSatu.text = quizResult.KuisSatu.toString()
                tvHasilKuisDua.text = quizResult.KuisDua.toString()
                tvHasilKuisTiga.text = quizResult.KuisTiga.toString()
                tvHasilKuisEmpat.text = quizResult.KuisEmpat.toString()
                tvHasilKuisLima.text = quizResult.KuisLima.toString()
                tvHasilKuisFinal.text = finalScore.toInt().toString()
                tvKuis1.text = textJudulNilai[0]
                tvKuis2.text = textJudulNilai[1]
                tvKuis3.text = textJudulNilai[2]
                tvKuis4.text = textJudulNilai[3]
                tvKuis5.text = textJudulNilai[4]
            }
        }
        binding.fabBack.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun getUser(): userData {
        val sharedPreferences = getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        return userData(
            getUID(),
            sharedPreferences.getString("userName", "")!!,
            sharedPreferences.getString("userNIS", "")!!,
            sharedPreferences.getString("userEmail", "")!!,
            sharedPreferences.getString("userKelas", "")!!
        )
    }
}