package com.cslab.blovi.configure.token

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component ("customJwtTokenUtil")
class JwtTokenUtil(@Value("\${jwt.secret}") private val secret: String) {

    private  val expiration: Long = 604800

    fun generateToken(userDetails: UserDetails): String{
        return Jwts.builder()
            .setSubject(userDetails.username)
            .setExpiration(Date(System.currentTimeMillis()+expiration*1000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }
    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        if (token.isNullOrBlank()) {
            return false
        }
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun extractUsername(token: String): String {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }

    private fun isTokenExpired(token: String): Boolean {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.expiration.before(Date())
    }
}