package com.freewill.config.security

import com.freewill.security.jwt.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { basic -> basic.disable() }
            .csrf { csrf -> csrf.disable() }
            .formLogin { form -> form.disable() }
            .headers { header -> header.frameOptions { frameOptions -> frameOptions.disable() } }
            .sessionManagement { setSessionManagement() }
            .authorizeHttpRequests(setAuthorizeHttpRequests())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    private fun setSessionManagement(): Customizer<SessionManagementConfigurer<HttpSecurity>> =
        Customizer { config: SessionManagementConfigurer<HttpSecurity> ->
            config.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS,
            )
        }

    private fun setAuthorizeHttpRequests(): Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> =
        Customizer { requests ->
            requests
                .requestMatchers(
                    AntPathRequestMatcher("/docs/api-docs.html"),
                    AntPathRequestMatcher("/api/v1/auth/**"),
                ).permitAll()
//                .requestMatchers(AntPathRequestMatcher("/api/v1/**")).hasRole("USER")
                .anyRequest().authenticated()
        }
}
