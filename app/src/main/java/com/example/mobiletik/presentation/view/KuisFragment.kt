package com.example.mobiletik.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentKuisBinding
import com.example.mobiletik.domain.usecase.UserData.getQuizResultFromSharedpref

class KuisFragment : Fragment(R.layout.fragment_kuis) {

    private lateinit var binding : FragmentKuisBinding

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKuisBinding.bind(view)
        val warning = resources.getString(R.string.kuisTelahDikerjakan)
        val welcome = resources.getString(R.string.selamatMengerjakan)
        val quizResult = getQuizResultFromSharedpref(requireActivity())
        val btnText : Array<String> = resources.getStringArray(R.array.judulKuis)
        with(binding) {
            btnKuis1.text = btnText[0]
            btnKuis2.text = btnText[1]
            btnKuis3.text = btnText[2]
            btnKuis4.text = btnText[3]
            btnKuis5.text = btnText[4]

            btnKuis1.setOnClickListener {
                if (quizResult.KuisSatu >= 80) {
                    toast(context!!, warning)
                } else {
                    toast(context!!, welcome)
                    startActivity(
                        Intent(context, KuisActivity::class.java).putExtra(
                            "nomor",
                            "KuisSatu"
                        )
                    )
                }
            }

            btnKuis2.setOnClickListener {
                if (quizResult.KuisDua >= 80) {
                    toast(context!!, warning)
                } else {
                    toast(context!!, welcome)
                    startActivity(
                        Intent(context, KuisActivity::class.java).putExtra(
                            "nomor",
                            "KuisDua"
                        )
                    )
                }
            }

            btnKuis3.setOnClickListener {
                if (quizResult.KuisTiga >= 80) {
                    toast(context!!, warning)
                } else {
                    toast(context!!, welcome)
                    startActivity(
                        Intent(context, KuisActivity::class.java).putExtra(
                            "nomor",
                            "KuisTiga"
                        )
                    )
                }
            }

            btnKuis4.setOnClickListener {
                if (quizResult.KuisEmpat >= 80) {
                    toast(context!!, warning)
                } else {
                    toast(context!!, welcome)
                    startActivity(
                        Intent(context, KuisActivity::class.java).putExtra(
                            "nomor",
                            "KuisEmpat"
                        )
                    )
                }

                btnKuis5.setOnClickListener {
                    if (quizResult.KuisLima >= 80) {
                        toast(context!!, warning)
                    } else {
                        toast(context!!, welcome)
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
    }

    private fun toast(context : Context, message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}