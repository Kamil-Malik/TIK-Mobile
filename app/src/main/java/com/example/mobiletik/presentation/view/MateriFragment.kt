package com.example.mobiletik.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentMateriBinding


class MateriFragment : Fragment(R.layout.fragment_materi) {

    private lateinit var binding : FragmentMateriBinding

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMateriBinding.bind(view)

        val textButton = resources.getStringArray(R.array.judulMateri)
        with(binding) {
            bab1.text = textButton[0]
            bab2.text = textButton[1]
            bab3.text = textButton[2]
            bab4.text = textButton[3]
            bab5.text = textButton[4]
        }

        binding.bab1.setOnClickListener{
            startActivity(Intent(requireActivity(), MateriActivity::class.java).putExtra("bab",0))
        }
        binding.bab2.setOnClickListener{
            startActivity(Intent(requireActivity(), MateriActivity::class.java).putExtra("bab",1))
        }
        binding.bab3.setOnClickListener{
            startActivity(Intent(requireActivity(), MateriActivity::class.java).putExtra("bab",2))
        }
        binding.bab4.setOnClickListener{
            startActivity(Intent(requireActivity(), MateriActivity::class.java).putExtra("bab",3))
        }
        binding.bab5.setOnClickListener{
            startActivity(Intent(requireActivity(), MateriActivity::class.java).putExtra("bab",4))
        }
    }
}