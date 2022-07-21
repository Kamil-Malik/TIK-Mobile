package com.example.mobiletik.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MateriActivityViewmodel : ViewModel() {
    val namaFile = MutableLiveData<String>()
    fun getFile(): LiveData<String> {
        return namaFile
    }
    val urlMateri = MutableLiveData<String>()
}