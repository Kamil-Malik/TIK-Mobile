package com.example.mobiletik.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentKuisBinding
import com.example.mobiletik.domain.usecase.getQuizResultFromSharedpref.quizResult
import com.example.mobiletik.domain.usecase.quizAttempt.quizAttempt

class KuisFragment : Fragment(R.layout.fragment_kuis) {

    private lateinit var binding: FragmentKuisBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKuisBinding.bind(view)
        val msg = resources.getStringArray(R.array.fragment_kuis)
        val quizResult = quizResult(requireActivity())
        val quizAttempt = quizAttempt(requireActivity())
        val btnText: Array<String> = resources.getStringArray(R.array.judulKuis)
        with(binding) {
            btnKuis1.text = btnText[0]
            btnKuis2.text = btnText[1]
            btnKuis3.text = btnText[2]
            btnKuis4.text = btnText[3]
            btnKuis5.text = btnText[4]

            btnKuis1.setOnClickListener {
                if (quizResult.KuisSatu >= 80) {
                    toast(context!!, msg[0])
                } else {
                    if (quizResult.KuisDua >= 80) {
                        toast(requireContext(), msg[0])
                    } else {
                        if (quizResult.KuisSatu < 80 && quizAttempt.kuisSatuAttempt < 3) {
                            toast(requireContext(), msg[1])
                            start("KuisSatu")
                        } else if (quizResult.KuisSatu < 80 && quizAttempt.kuisSatuAttempt.toInt() == 0) {
                            toast(requireContext(), msg[2])
                            start("KuisSatu")
                        } else {
                            toast(requireContext(), "Anda telah mengerjakan sebanyak 3 kali")
                        }
                    }
                }
            }

            btnKuis2.setOnClickListener {
                if (quizResult.KuisDua >= 80) {
                    toast(requireContext(), msg[0])
                } else {
                    if (quizResult.KuisDua < 80 && quizAttempt.kuisDuaAttempt < 3) {
                        toast(requireContext(), msg[1])
                        start("KuisDua")
                    } else if (quizResult.KuisDua < 80 && quizAttempt.kuisDuaAttempt.toInt() == 0) {
                        toast(requireContext(), msg[2])
                        start("KuisDua")
                    } else {
                        toast(requireContext(), "Anda telah mengerjakan sebanyak 3 kali")
                    }
                }
            }

            btnKuis3.setOnClickListener {
                if (quizResult.KuisTiga >= 80) {
                    toast(requireContext(), msg[0])
                } else {
                    if (quizResult.KuisTiga < 80 && quizAttempt.kuisTigaAttempt < 3) {
                        toast(requireContext(), msg[1])
                        start("KuisTiga")
                    } else if (quizResult.KuisTiga < 80 && quizAttempt.kuisTigaAttempt.toInt() == 0) {
                        toast(requireContext(), msg[2])
                        start("KuisTiga")
                    } else {
                        toast(requireContext(), "Anda telah mengerjakan sebanyak 3 kali")
                    }
                }
            }

            btnKuis4.setOnClickListener {
                if (quizResult.KuisEmpat >= 80) {
                    toast(requireContext(), msg[0])
                } else {
                    if (quizResult.KuisEmpat < 80 && quizAttempt.kuisEmpatAttempt < 3) {
                        toast(requireContext(), msg[1])
                        start("KuisEmpat")
                    } else if (quizResult.KuisEmpat < 80 && quizAttempt.kuisEmpatAttempt.toInt() == 0) {
                        toast(requireContext(), msg[2])
                        start("KuisEmpat")
                    } else {
                        toast(requireContext(), "Anda telah mengerjakan sebanyak 3 kali")
                    }
                }
            }

            btnKuis5.setOnClickListener {
                if (quizResult.KuisLima >= 80) {
                    toast(requireContext(), msg[0])
                } else {
                    if (quizResult.KuisLima < 80 && quizAttempt.kuisLimaAttempt < 3) {
                        toast(requireContext(), msg[1])
                        start("KuisLima")
                    } else if (quizResult.KuisLima < 80 && quizAttempt.kuisLimaAttempt.toInt() == 0) {
                        toast(requireContext(), msg[2])
                        start("KuisLima")
                    } else {
                        toast(requireContext(), "Anda telah mengerjakan sebanyak 3 kali")
                    }
                }
            }

        }
    }

    private fun start(judul: String) {
        Intent(context, KuisActivity::class.java).putExtra("nomor", judul).also {
            startActivity(it)
        }
    }

    private fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}