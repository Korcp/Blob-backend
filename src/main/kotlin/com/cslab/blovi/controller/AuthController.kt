package com.cslab.blovi.controller

import com.cslab.blovi.configure.Entity.User
import com.cslab.blovi.configure.token.JwtTokenUtil
import com.cslab.blovi.service.Impl.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {

    @PostMapping("/register")
    fun register(@RequestBody userRequest: UserRequest) {
        val user = User(0, userRequest.username, userRequest.password)
    }

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/login")
    fun login(@RequestBody userRequest: UserRequest): String {
        val user = userService.findByUsername(userRequest.username)
        if (user != null && user.password == userRequest.password) {
            // UserDetailsServiceImpl을 사용합니다
            val userDetails = userDetailsServiceImpl.loadUserByUsername(userRequest.username)
            // 주입된 인스턴스를 사용하여 메서드를 호출합니다
            val jwtToken = jwtTokenUtil.generateToken(userDetails)
            logger.info("JWT 토큰: $jwtToken")
            return jwtToken
        } else {
            // 유효하지 않은 로그인을 처리합니다
            logger.error("유효하지 않은 로그인 시도: ${userRequest.username}")
            throw RuntimeException("유효하지 않은 자격 증명")
        }
    }
}

data class UserRequest(val username: String, val password: String)