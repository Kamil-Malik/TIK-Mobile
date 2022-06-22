package com.example.mobiletik.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobiletik.databinding.ActivityMainBinding
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

        if(nama.isNotEmpty()){
            binding.nama.text = "Nama\t\t\t\t: $nama"
        }
        if(nis.isNotEmpty()){
            binding.nis.text = "NIS\t\t\t\t\t: $nis"
        }
        if(email.isNotEmpty()){
            binding.email.text = "Email\t\t\t\t: $email"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}