package com.example.mobiletik.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentKuisBinding
import com.example.mobiletik.domain.usecase.DatabaseUser
import com.example.mobiletik.domain.usecase.UserData.getQuizResultFromSharedpref

class KuisFragment : Fragment(R.layout.fragment_kuis) {

    private lateinit var binding : FragmentKuisBinding

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKuisBinding.bind(view)
        val warning = resources.getString(R.string.kuisTelahDikerjakan)
        val welcome = resources.getString(R.string.selamatMengerjakan)
        val btnText : Array<String> = resources.getStringArray(R.array.judulKuis)
        with(binding) {
            btnKuis1.text = btnText[0]
            btnKuis2.text = btnText[1]
            btnKuis3.text = btnText[2]
            btnKuis4.text = btnText[3]
            btnKuis5.text = btnText[4]
        }

        val quizResult = getQuizResultFromSharedpref(requireActivity())
        binding.btnKuis1.setOnClickListener {
            if (quizResult.KuisSatu >= 80) {
                Toast.makeText(context, warning, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, welcome, Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(context, KuisActivity::class.java).putExtra(
                        "nomor",
                        "KuisSatu"
                    )
                )
            }
        }
        binding.btnKuis2.setOnClickListener {
            if (quizResult.KuisDua >= 80) {
                Toast.makeText(context, warning, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, welcome, Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(context, KuisActivity::class.java).putExtra(
                        "nomor",
                        "KuisDua"
                    )
                )
            }
        }
        binding.btnKuis3.setOnClickListener {
            if (quizResult.KuisTiga >= 80) {
                Toast.makeText(context, warning, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, welcome, Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(context, KuisActivity::class.java).putExtra(
                        "nomor",
                        "KuisTiga"
                    )
                )
            }
        }
        binding.btnKuis4.setOnClickListener {
            if (quizResult.KuisEmpat >= 80) {
                Toast.makeText(context, warning, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, welcome, Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(context, KuisActivity::class.java).putExtra(
                        "nomor",
                        "KuisEmpat"
                    )
                )
            }
        }
        binding.btnKuis5.setOnClickListener {
            if (quizResult.KuisLima >= 80) {
                Toast.makeText(context, warning, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, welcome, Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(context, KuisActivity::class.java).putExtra(
                        "nomor",
                        "KuisLima"
                    )
                )
            }
        }
    }
}