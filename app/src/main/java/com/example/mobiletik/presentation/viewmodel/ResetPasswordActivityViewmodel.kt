package com.example.mobiletik.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.mobiletik.domain.usecase.Authentication
import com.example.mobiletik.presentation.view.ResetPasswordActivity

class ResetPasswordActivityViewmodel : ViewModel() {

    fun checkForm(mActivity : ResetPasswordActivity) {
        val email = mActivity.binding.userEmail.text.toString()
        if (email.isEmpty()) {
            mActivity.binding.userEmail.error = "Silahkan masukkan Email terlebih dahulu"
        } else if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mActivity.binding.userEmail.error = "Silahkan masukkan Email yang valid"
        } else if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val auth = Authentication
            auth.resetPassword(mActivity, email)
        }
    }
}