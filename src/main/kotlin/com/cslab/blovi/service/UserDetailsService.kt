package com.cslab.blovi.service


import com.cslab.blovi.configure.mapper.UserMapper
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userMapper: UserMapper) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userMapper.findUserByEmail(email)
            ?: throw UsernameNotFoundException("해당하는 사용자를 찾을 수 없습니다: $email")

        return User.builder()
            .username(user.email)
            .password(user.password)
            .authorities(emptyList())
            .build()
    }
}

