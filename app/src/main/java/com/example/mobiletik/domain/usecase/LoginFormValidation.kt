package com.example.mobiletik.domain.usecase

import android.util.Patterns

object LoginFormValidation {

    /**
     * Jika Email Kosong -> False
     * Jika Password Kosong -> False
     * Jika Email tidak valid -> False
     * Jika semua benar -> True
     */

    data class ValidationResult(
        val result : Boolean,
        val errorMessage : String?
    )

    private const val paramIsNotFullfilled = "Email atau Password tidak boleh kosong"
    private const val invalidEmail = "Email tidak Valid"

    fun validate(
        email : String,
        password : String
    ) : ValidationResult {
        if (email.isEmpty() || password.isEmpty()) {
            return ValidationResult(false, paramIsNotFullfilled)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, invalidEmail)
        }
        return ValidationResult(true, null)
    }
}