package com.cslab.blovi.dto

import com.nimbusds.oauth2.sdk.token.AccessToken
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.User

data class ResponseDTO(
    var blog_name: String? = null,
    var token: String? = null
) {
    constructor(user: UserDTO, accessToken: String) : this(
        blog_name = user.blog_name,
        token = "bearer $accessToken"
    )
}