package com.example.mobiletik.domain.usecase

import android.util.Patterns

object RegisterFormValidation {

    /**
     * Jika Username Kosong -> False
     * Jika Password Kosong -> False
     * Jika NIS Kosong -> False
     * Jika Email tidak Valid -> False
     * Jika NIS < 4 -> False
     * Jika Kelas Kosong -> False
     * Jika Password < 8 -> False
     */

    data class ValidationResult(
        var result: Boolean,
        var errorMessageNama: String?,
        var errorMessageNIS: String?,
        var errorMessageKelas: String?,
        var errorMessageEmail: String?,
        var errorMessagePassword: String?
    )

    private const val emptyName = "Nama tidak boleh kosong"
    private const val emptyNIS = "NIS tidak boleh kosong"
    private const val nisTooShort = "NIS tidak boleh kurang dari 4 karakter"
    private const val emptyClass = "Kelas tidak boleh kosong"
    private const val emptyEmail = "Email tidak boleh kosong"
    private const val invalidEmail = "Email tidak Valid"
    private const val emptyPassword = "Password tidak boleh kosong"
    private const val passwordTooShort = "Password tidak boleh kurang dari 8 karakter"

    fun validate(
        userName: String,
        userNIS: String,
        userEmail: String,
        userKelas: String,
        userPassword: String
    ): ValidationResult {
        var result = ValidationResult(false, null, null, null, null, null)
        with(this) {
            if (userName.isEmpty()) {
                result.errorMessageNama = emptyName
            }
            if (userNIS.isEmpty()) {
                result.errorMessageNIS = emptyNIS
            }
            if (userNIS.length < 4) {
                result.errorMessageNIS = nisTooShort
            }
            if (userKelas.isEmpty()) {
                result.errorMessageKelas = emptyClass
            }
            if (userEmail.isEmpty()) {
                result.errorMessageEmail = emptyEmail
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                result.errorMessageEmail = invalidEmail
            }
            if (userPassword.isEmpty()) {
                result.errorMessagePassword = emptyPassword
            }
            if (userPassword.length <= 8) {
                result.errorMessagePassword = passwordTooShort
            }
            if (userName.isNotEmpty() && userNIS.isNotEmpty() && userNIS.length > 4 && userKelas.isNotEmpty() && userEmail.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(userEmail)
                    .matches() && userPassword.isNotEmpty() && userPassword.length >= 8
            ) {
                result.result = true
            }
        }
        return result
    }
}