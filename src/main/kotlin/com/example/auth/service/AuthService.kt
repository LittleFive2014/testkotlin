package com.example.auth.service

import com.example.auth.dto.LoginRequest
import com.example.auth.entity.User
import com.example.auth.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordService: PasswordService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        )
    }

    fun authenticate(loginRequest: LoginRequest): User {
        val user = userRepository.findByUsername(loginRequest.username)
            ?: throw BadCredentialsException("Invalid credentials")

        if (!passwordService.matches(loginRequest.password, user.password)) {
            throw BadCredentialsException("Invalid credentials")
        }

        return user
    }
}
