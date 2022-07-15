package com.example.mobiletik.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentMoreBinding
import com.example.mobiletik.presentation.viewmodel.MainActivityViewmodel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MoreFragment : Fragment(R.layout.fragment_more) {

    private lateinit var binding : FragmentMoreBinding

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)

        ViewModelProvider(this)[MainActivityViewmodel::class.java]

        binding.btnLisensi.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    LicenseActivity::class.java
                )
            )
        }
        binding.btnProfil.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    ProfileActivity::class.java
                )
            )
        }
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity!!.finish()
        }
    }
}