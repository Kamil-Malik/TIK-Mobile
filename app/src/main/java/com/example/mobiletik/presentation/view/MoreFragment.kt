package com.example.mobiletik.presentation.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentMoreBinding
import com.example.mobiletik.domain.usecase.ExportCSV.exportCSV
import com.example.mobiletik.domain.usecase.Logout.Logout
import com.example.mobiletik.domain.usecase.UserData.getUserDataFromSharedpref

class MoreFragment : Fragment(R.layout.fragment_more) {

    private lateinit var binding: FragmentMoreBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)

        val nis = getUserDataFromSharedpref(requireActivity()).userNIS
        if (nis == "802019") {
            Log.d(TAG, "onViewCreated: User adalah admin")
            setAdmin()
        } else {
            Log.d(TAG, "onViewCreated: User bukan admin")
            setUser()
        }

        binding.btnLisensi.setOnClickListener { //Btn Daftar Lisensi
            Log.d(TAG, "onViewCreated: Tombol Daftar Lisensi ditekan")
            Intent(requireContext(), LicenseActivity::class.java).also {
                requireContext().startActivity(it)
            }
        }

        binding.btnProfil.setOnClickListener { //Btn Profil
            Log.d(TAG, "onViewCreated: Tombol Profil Ditekan")
            Intent(requireContext(), ProfileActivity::class.java).also {
                requireContext().startActivity(it)
            }
        }

        binding.btnLogout.setOnClickListener { //Btn Logout
            Log.d(TAG, "onViewCreated: Tombol Logout ditekan")
            Logout(requireActivity())
        }

        binding.btnExport.setOnClickListener {
            Log.d(TAG, "onViewCreated: Tombol Export ditekan")
            exportCSV(requireActivity())
        }

    }

    private fun setUser() {
        binding.btnExport.isVisible = false
    }

    private fun setAdmin() {
        binding.btnExport.isVisible = true
    }
}