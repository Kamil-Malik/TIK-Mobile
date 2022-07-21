package com.example.mobiletik.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentMateriBinding
import com.example.mobiletik.databinding.FragmentPdfBinding
import com.github.barteksc.pdfviewer.PDFView

class FragmentPdf : Fragment(R.layout.fragment_pdf) {

    private lateinit var binding : FragmentPdfBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPdfBinding.bind(view)
        loadPDF()
    }

    override fun onResume() {
        super.onResume()
        loadPDF()
    }

    private fun loadPDF() {
        val data = arguments!!.getString("res", "")
        binding.pdfReader.fromAsset("$data.pdf").swipeHorizontal(true).load()
    }
}