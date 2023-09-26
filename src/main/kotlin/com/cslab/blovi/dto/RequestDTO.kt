package com.cslab.blovi.dto

data class RequestDTO(
    val email: String,
    val password: String
) {
    // Getter for email
    fun getEmail(): String {
        return email
    }

    // Getter for password
    fun getPassword(): String {
        return password
    }
}
