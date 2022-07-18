package com.example.mobiletik.domain.usecase

import android.util.Patterns

object RegisterFormValidation {

    /**
     * Jika Username Kosong -> False
     * Jika Password Kosong -> False
     * Jika NIS Kosong -> False
     * Jika Email tidak Valid -> False
     * Jika NIS < 4 -> False
     * Jika Password < 8 -> False
     */

    data class ValidationResult(
        val result: Boolean,
        val errorMessage: String?
    )

    private const val emptyForm = "Semua form harus terisi"
    private const val invalidEmail = "Email tidak Valid"
    private const val nisTooShort = "NIS tidak boleh kurang dari 4 karakter"
    private const val passwordTooShort = "Password tidak boleh kurang dari 8 karakter"
    private const val emptyClass = "Kelas tidak boleh kosong"

    fun validate(
        userName: String,
        userNIS: String,
        userEmail: String,
        userKelas: String,
        userPassword: String
    ): ValidationResult {
        if (userName.isEmpty() || userNIS.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            return ValidationResult(false, emptyForm)
        }
        if (userNIS.length < 4) {
            return ValidationResult(false, nisTooShort)
        }
        if (userKelas.isEmpty()) {
            return ValidationResult(false, emptyClass)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            return ValidationResult(false, invalidEmail)
        }
        if (userPassword.length < 8) {
            return ValidationResult(false, passwordTooShort)
        }
        return ValidationResult(true, null)
    }
}