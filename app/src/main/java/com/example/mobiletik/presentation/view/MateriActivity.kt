package com.example.mobiletik.presentation.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobiletik.R
import com.example.mobiletik.databinding.ActivityMateriBinding

class MateriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMateriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val judulMateri: Array<String> = resources.getStringArray(R.array.listID)
        val listID: Array<String> = resources.getStringArray(R.array.urlMateri)
        val arrayFile: Array<String> = resources.getStringArray(R.array.namaFile)
        val indeks = intent.getIntExtra("bab", 0)
        binding.toolbar.title = judulMateri[indeks]
        val namafile = arrayFile[indeks]
        val videoID = listID[indeks]
        val materi = FragmentPdf()
        val video = VideoFragment()

        replaceFragment(materi, namafile)
        binding.btmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.materiPDF -> {
                    Log.d(TAG, "onCreate: materi ditekan")
                    replaceFragment(materi, namafile)
                    Log.d(TAG, "onCreate: replace materi dipanggil")
                }
                R.id.video -> {
                    Log.d(TAG, "onCreate: video ditekan")
                    replaceFragment(video, videoID)
                    Log.d(TAG, "onCreate: replace video dipanggil")
                }
                R.id.back -> onBackPressed()
            }
            true
        }
    }

    private fun replaceFragment(mFragment: Fragment, stringParam: String) {
        val bundle = Bundle()
        bundle.putString("res", stringParam)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentView, mFragment).apply {
            mFragment.arguments = bundle
        }.commitNow()
    }

    override fun onBackPressed() {
        finish()
    }
}