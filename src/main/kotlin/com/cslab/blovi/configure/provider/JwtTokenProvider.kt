package com.cslab.blovi.configure.provider

import org.springframework.beans.factory.annotation.Value

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import com.cslab.blovi.service.Impl.UserDetailsServiceImpl
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails

@Component
class JwtTokenProvider(private val customUserDetailsService : UserDetailsServiceImpl) {

    @Value("\${spring.jwt.secret}")
    private lateinit var secretKey: String

    private val ACCESS_TOKEN_VALID_TIME: Long = 1000L * 60 * 30 // 30분

    @Bean // 틀릴수도잇음
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createAccessToken(email: String, role: String): String {
        val claims: Claims = Jwts.claims().setSubject(email)
        claims["role"] = role
        val now = Date()

        return Jwts.builder()
            .setHeaderParam(io.jsonwebtoken.Header.TYPE, io.jsonwebtoken.Header.JWT_TYPE)
            .setClaims(claims)
            .setIssuedAt(now)
            .setIssuer("cloudwi")
            .setExpiration(Date(now.time + ACCESS_TOKEN_VALID_TIME))
            .setSubject(email)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(getUserEmail(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUserEmail(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("AccessToken")
    }

    fun validateAccessToken(token: String): Boolean {
        var modifiedToken = bearerRemove(token) //bearer제거해서 토큰값을가져온다
        try {
            val claims: io.jsonwebtoken.Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(modifiedToken)
            return !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    public fun bearerRemove(token: String): String {
        return token.substring("Bearer ".length)
    }
}
