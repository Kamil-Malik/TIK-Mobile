package com.example.mobiletik.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentKuisBinding


class KuisFragment : Fragment(R.layout.fragment_kuis) {

    private lateinit var binding : FragmentKuisBinding

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKuisBinding.bind(view)

        binding.btnKuis1.setOnClickListener {
            startActivity(Intent(context, KuisActivity::class.java).putExtra("nomor", "KuisSatu"))
        }
        binding.btnKuis2.setOnClickListener {
            startActivity(Intent(context, KuisActivity::class.java).putExtra("nomor", "KuisDua"))
        }
    }
}