package com.example.mobiletik.presentation.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentKuisBinding
import com.example.mobiletik.domain.usecase.GetQuizResultFromSharedPref.quizResult
import com.example.mobiletik.domain.usecase.QuizAttempt.quizAttempt

class KuisFragment : Fragment(R.layout.fragment_kuis) {

    private lateinit var binding: FragmentKuisBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKuisBinding.bind(view)

        val msg = resources.getStringArray(R.array.fragment_kuis)
        Log.d(TAG, "onViewCreated: Array Pesan didapatkan. $msg")

        val quizResult = quizResult(requireActivity())
        Log.d(TAG, "onViewCreated: $quizResult")

        val quizAttempt = quizAttempt(requireActivity())
        Log.d(TAG, "onViewCreated: $quizAttempt")

        val btnText: Array<String> = resources.getStringArray(R.array.judulKuis)
        Log.d(TAG, "onViewCreated: $btnText")

        with(binding) {
            btnKuis1.text = btnText[0]
            btnKuis2.text = btnText[1]
            btnKuis3.text = btnText[2]
            btnKuis4.text = btnText[3]
            btnKuis5.text = btnText[4]
            Log.d(TAG, "onViewCreated: Text selesai dipasang")
        }

        binding.btnKuis1.setOnClickListener {
            Log.d(TAG, "onViewCreated: Kuis1 Ditekan")
            if (quizResult.KuisSatu >= 80) {
                toast(context!!, msg[0])
            } else if (quizResult.KuisSatu < 80 && quizAttempt.kuisSatuAttempt.toInt() == 0) {
                toast(context!!, msg[1])
                start("KuisSatu")
            } else if (quizResult.KuisSatu < 80 && quizAttempt.kuisSatuAttempt.toInt() < 3) {
                toast(context!!, msg[2])
                start("KuisSatu")
            } else if (quizResult.KuisSatu < 80 && quizAttempt.kuisSatuAttempt.toInt() == 3) {
                toast(context!!, msg[3])
            }
        }

        binding.btnKuis2.setOnClickListener {
            Log.d(TAG, "onViewCreated: Kuis2 Ditekan")
            if (quizResult.KuisDua >= 80) {
                toast(context!!, msg[0])
            } else if (quizResult.KuisDua < 80 && quizAttempt.kuisDuaAttempt.toInt() == 0) {
                toast(context!!, msg[1])
                start("KuisDua")
            } else if (quizResult.KuisDua < 80 && quizAttempt.kuisDuaAttempt.toInt() < 3) {
                toast(context!!, msg[2])
                start("KuisDua")
            } else if (quizResult.KuisDua < 80 && quizAttempt.kuisDuaAttempt.toInt() == 3) {
                toast(context!!, msg[3])
            }
        }

        binding.btnKuis3.setOnClickListener {
            Log.d(TAG, "onViewCreated: Kuis3 Ditekan")
            if (quizResult.KuisTiga >= 80) {
                toast(context!!, msg[0])
            } else if (quizResult.KuisTiga <= 80 && quizAttempt.kuisTigaAttempt.toInt() == 0) {
                toast(context!!, msg[1])
                start("KuisTiga")
            } else if (quizResult.KuisTiga <= 80 && quizAttempt.kuisTigaAttempt.toInt() < 3) {
                toast(context!!, msg[2])
                start("KuisTiga")
            } else if (quizResult.KuisTiga <= 80 && quizAttempt.kuisTigaAttempt.toInt() == 3) {
                toast(context!!, msg[3])
            }
        }

        binding.btnKuis4.setOnClickListener {
            Log.d(TAG, "onViewCreated: Kuis4 Ditekan")
            if (quizResult.KuisEmpat >= 80) {
                toast(context!!, msg[0])
            } else if (quizResult.KuisEmpat <= 80 && quizAttempt.kuisEmpatAttempt.toInt() == 0) {
                toast(context!!, msg[1])
                start("KuisEmpat")
            } else if (quizResult.KuisEmpat <= 80 && quizAttempt.kuisEmpatAttempt.toInt() < 3) {
                toast(context!!, msg[2])
                start("KuisEmpat")
            } else if (quizResult.KuisEmpat <= 80 && quizAttempt.kuisEmpatAttempt.toInt() == 3) {
                toast(context!!, msg[3])
            }
        }

        binding.btnKuis5.setOnClickListener {
            Log.d(TAG, "onViewCreated: Kuis5 Ditekan")
            if (quizResult.KuisLima >= 80) {
                toast(context!!, msg[0])
            } else if (quizResult.KuisLima <= 80 && quizAttempt.kuisLimaAttempt.toInt() == 0) {
                toast(context!!, msg[1])
                start("KuisLima")
            } else if (quizResult.KuisLima <= 80 && quizAttempt.kuisLimaAttempt.toInt() < 3) {
                toast(context!!, msg[2])
                start("KuisLima")
            } else if (quizResult.KuisLima <= 80 && quizAttempt.kuisLimaAttempt.toInt() == 3) {
                toast(context!!, msg[3])
            }
        }
    }

    fun start(judul: String) {
        Log.d(TAG, "start: Activity Kuis dipanggil")
        Intent(context, KuisActivity::class.java).putExtra("nomor", judul).also {
            startActivity(it)
        }
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}