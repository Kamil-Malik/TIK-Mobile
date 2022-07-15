package com.example.mobiletik.domain.usecase

import android.util.Patterns

object ResetPasswordValidation {

    data class ValidationResult(
        val result : Boolean,
        val errorMessage : String?
    )

    private const val emptyForm = "Silahkan masukkan email terlebih dahulu"
    private const val invalidEmail = "Email tidak Valid"

    fun validate(email : String) : ValidationResult {
        if(email.isEmpty()) {
            return ValidationResult(false, emptyForm)
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, invalidEmail)
        }
        return ValidationResult(true, null)
    }

}