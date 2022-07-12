package com.example.mobiletik.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentChatBinding
import com.example.mobiletik.domain.usecase.Authentication
import com.example.mobiletik.model.data.Chat
import com.example.mobiletik.presentation.adapter.ChatAdapter
import com.example.mobiletik.presentation.viewmodel.MainActivityViewmodel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding
    private val sharedViewModel: MainActivityViewmodel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val adapter = ChatAdapter(sharedViewModel.chatData)
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                async {
                    binding.rvChat.layoutManager = layoutManager
                    binding.rvChat.adapter = adapter
                    binding.rvChat.scrollToPosition(adapter.itemCount - 1)
                }.await()
                binding.btnKirim.setOnClickListener {
                    sendMessage(binding.rvChat, adapter)
                }
            }
        }
    }

    private fun sendMessage(rvChat: RecyclerView, adapter: ChatAdapter) {
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
            CoroutineScope(Dispatchers.IO).launch {
                val database = Firebase.database.getReference("Chat")
                database.push().setValue(message).await()
                withContext(Dispatchers.Main){
                    rvChat.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }

}