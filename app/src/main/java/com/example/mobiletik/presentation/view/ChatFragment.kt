package com.example.mobiletik.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentChatBinding
import com.example.mobiletik.domain.usecase.Authentication
import com.example.mobiletik.domain.usecase.DatabaseChat
import com.example.mobiletik.model.data.Chat
import com.example.mobiletik.presentation.viewmodel.MainActivityViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding : FragmentChatBinding
    private val sharedViewModel : MainActivityViewmodel by activityViewModels()


    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)
        val adapter = sharedViewModel.adapter
        CoroutineScope(Dispatchers.Main).launch{
            with(binding) {
                rvChat.adapter = adapter
                rvChat.layoutManager = LinearLayoutManager(activity)
                rvChat.scrollToPosition(adapter.itemCount - 1)
                btnKirim.setOnClickListener {
                    sendMessage()
                }
            }
        }
    }

    private fun sendMessage() {
        val activity = activity
        val sharedPref =
            activity!!.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("userName", "")
        val pesan = binding.isiPesan.text.toString()
        val message = Chat(
            message = pesan,
            senderName = nama!!,
            senderUID = Authentication.getUID()
        )
        binding.isiPesan.text.clear()
        if (pesan.isEmpty()) {
            binding.isiPesan.error = "Silahkan masukkan pesan terlebih dahulu"
        } else {
            DatabaseChat.sendMessage(message)
        }
    }

}