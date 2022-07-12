package com.example.mobiletik.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletik.databinding.ChatAdapterBinding
import com.example.mobiletik.domain.usecase.Authentication
import com.example.mobiletik.model.data.Chat

class ChatAdapter(private val chatData : List<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ChatAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ChatAdapterBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.binding.apply {
            if (Authentication.getUID() == chatData[position].senderUID) {
                pesanDikirim.isVisible = true
                chatMasuk.isVisible = false
                pesanDikirim.text = chatData[position].message
            } else {
                chatMasuk.isVisible = true
                pesanDikirim.isVisible = false
                senderName.text = chatData[position].senderName
                pesanDiterima.text = chatData[position].message
            }
        }
    }

    override fun getItemCount() : Int {
        return chatData.size
    }
}