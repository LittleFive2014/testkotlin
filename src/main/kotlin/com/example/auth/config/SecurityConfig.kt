package com.example.auth.config

import com.example.auth.service.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.config.Customizer.withDefaults

@Configuration
@EnableWebSecurity
class SecurityConfig(private val authService: AuthService) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/login", "/h2-console/**", "/static/**").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form.loginPage("/login")
                    .defaultSuccessUrl("/dashboard")
                    .permitAll()
            }
            .logout { logout ->
                logout.logoutSuccessUrl("/login?logout")
                    .permitAll()
            }
            .rememberMe { remember ->
                remember.key("uniqueAndSecret")
                    .tokenValiditySeconds(86400)
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .headers { headers ->
                headers.frameOptions { frameOptions ->
                    frameOptions.disable()
                }
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
