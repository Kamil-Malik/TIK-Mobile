package com.example.mobiletik.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val materi = MateriFragment()
        val chat = ChatFragment()
        val kuis = KuisFragment()
        val more = MoreFragment()

        applyFragment(materi)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.materi -> applyFragment(materi)
                R.id.diskusi -> applyFragment(chat)
                R.id.kuis -> applyFragment(kuis)
                R.id.more -> applyFragment(more)
            }
            true
        }
    }

    private fun applyFragment(mFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.fragmentView, mFragment)
        }.commit()
    }
}
