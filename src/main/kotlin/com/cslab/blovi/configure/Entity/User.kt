package com.cslab.blovi.configure.Entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val id: Long,
    val username: String,
    val password: String
)
