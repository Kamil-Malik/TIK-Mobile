package com.example.mobiletik.view.more.profil

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val nama = intent.getStringExtra("nama").toString()
        val nis = intent.getStringExtra("nis").toString()
        val email = intent.getStringExtra("email").toString()

        if (nama.isNotEmpty()) {
            binding.tvNama.text = nama
        }
        if (nis.isNotEmpty()) {
            binding.tvNis.text = nis
        }
        if (email.isNotEmpty()) {
            binding.tvEmail.text = email
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}