package com.example.mobiletik.view.more.profil

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.databinding.ActivityProfileBinding
import com.example.mobiletik.model.Database
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userProfile = Database.userData(this)

        try {
            binding.tvNama.text = userProfile.nama
            binding.tvNis.text = userProfile.nis
            binding.tvEmail.text = userProfile.email
        } catch (e : Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}