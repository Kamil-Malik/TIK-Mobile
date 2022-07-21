package com.example.mobiletik.domain.usecase

import android.content.ContentValues.TAG
import android.util.Log
import android.util.Patterns

object LoginFormValidation {

    /**
     * Jika Email  Kosong -> False
     * Jika Password Kosong -> False
     * Jika Email tidak valid -> False
     * Jika semua benar -> True
     */

    data class ValidationResult(
        val result: Boolean,
        val errorMessageEmail: String?,
        val errorMessagePassword: String?
    )

    private const val emptyEmail = "Email tidak boleh kosong"
    private const val emptyPassword = "Password  tidak boleh kosong"
    private const val invalidEmail = "Email tidak Valid"

    fun validate(
        email: String,
        password: String
    ): ValidationResult {
        if (email.isEmpty() && password.isEmpty()) {
            return ValidationResult(false, emptyEmail, emptyPassword)
        }
        if (email.isEmpty() && password.isNotEmpty()) {
            return ValidationResult(false, emptyEmail, null)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isEmpty()) {
            return ValidationResult(false, invalidEmail, emptyPassword)
        }
        Log.d(TAG, "validate: Validasi Berhasil")
        return ValidationResult(true, null, null)
    }
}