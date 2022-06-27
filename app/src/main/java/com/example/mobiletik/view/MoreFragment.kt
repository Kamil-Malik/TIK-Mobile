package com.example.mobiletik.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentMoreBinding
import com.example.mobiletik.view.more.license.LicenseActivity
import com.example.mobiletik.view.more.profil.ProfileActivity
import com.example.mobiletik.viewmodel.MainActivityViewmodel

class MoreFragment : Fragment(R.layout.fragment_more) {

    private lateinit var binding : FragmentMoreBinding

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)

        ViewModelProvider(this)[MainActivityViewmodel::class.java]

        binding.btnLisensi.setOnClickListener {
            startActivity(Intent(context, LicenseActivity::class.java))
        }
        binding.btnProfil.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java).apply {
                try {

                }catch (e: Exception){
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}