package com.example.mobiletik.presentation.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.FragmentVideoBinding
import com.example.mobiletik.domain.utility.Loading
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class VideoFragment : Fragment(R.layout.fragment_video) {

    private lateinit var binding: FragmentVideoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoBinding.bind(view)
        lifecycle.addObserver(binding.ytPlayer)
        val loading = Loading(requireActivity())
        loading.startLoading()
        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoid = arguments!!.getString("res")
                if (!videoid.isNullOrEmpty()) {
                    Log.w(TAG, "onReady: $videoid")
                    loading.dismissLoading()
                    youTubePlayer.loadVideo(videoid, 0F)
                }
            }


        }
        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(1).build()
        binding.ytPlayer.initialize(listener, options)
    }
}