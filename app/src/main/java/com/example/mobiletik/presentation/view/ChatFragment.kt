package com.example.mobiletik.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentChatBinding
import com.example.mobiletik.model.data.Chat
import com.example.mobiletik.model.usecase.Authentication
import com.example.mobiletik.model.usecase.DatabaseChat


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)

        val activity = activity
        val sharedPref =
            activity!!.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("userName", "")

        binding.btnKirim.setOnClickListener {
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
                DatabaseChat.sendMessage(context!!, message)
            }
        }
    }
}