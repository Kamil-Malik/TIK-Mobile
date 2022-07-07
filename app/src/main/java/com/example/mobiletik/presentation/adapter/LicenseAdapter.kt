package com.example.mobiletik.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletik.databinding.LicenseAdapterBinding
import com.example.mobiletik.model.data.LicenseData

class LicenseAdapter(private val licenseData : List<LicenseData>) :
    RecyclerView.Adapter<LicenseAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : LicenseAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LicenseAdapterBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.binding.apply {
            tvJudul.text = licenseData[position].judulLisensi
            tvLink.text = licenseData[position].linkLisensi
            tvLisensi1.text = licenseData[position].pemegangLisensi
            tvLisensi2.text = licenseData[position].pemegangLisensi2
            if (licenseData[position].pemegangLisensi2 == "") {
                tvLisensi2.isVisible = false
            }
        }
    }

    override fun getItemCount() : Int {
        return licenseData.size
    }


}