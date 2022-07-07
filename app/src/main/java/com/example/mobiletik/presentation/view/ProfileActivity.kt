package com.example.mobiletik.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.databinding.ActivityProfileBinding
import com.example.mobiletik.model.usecase.DatabaseUser

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userData = DatabaseUser.userDataOffline(this)
        val userKuis = DatabaseUser.quizDataOffline(this)

        try {
            binding.tvNama.text = userData.nama
            binding.tvNis.text = userData.nis
            binding.tvEmail.text = userData.email
            binding.nilaiKuisPertama.text = userKuis.KuisSatu
            binding.nilaiKuisKedua.text = userKuis.KuisDua
            binding.nilaiKuisKetiga.text = userKuis.KuisTiga
            binding.nilaiKuisKeempat.text = userKuis.KuisEmpat
            binding.nilaiKuisKelima.text = userKuis.KuisLima
        } catch (e : Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}