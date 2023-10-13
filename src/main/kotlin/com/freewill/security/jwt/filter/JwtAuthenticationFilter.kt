package com.freewill.security.jwt.filter

import com.freewill.global.common.exception.BearerSuffixNotExistsException
import com.freewill.security.jwt.util.JwtValidator
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtValidator: JwtValidator,
) : OncePerRequestFilter() {
    @Value("\${jwt.access-header}")
    private lateinit var accessHeader: String

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String? = getTokensFromHeader(request, accessHeader)
        token?.let {
            val accessToken = replaceBearerToBlank(it)

            val authentication: Authentication = jwtValidator.getAuthentication(accessToken)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokensFromHeader(request: HttpServletRequest, header: String): String? {
        return request.getHeader(header)
    }

    private fun replaceBearerToBlank(token: String): String {
        return if(!token.startsWith("Bearer ")) {
            throw BearerSuffixNotExistsException()
        } else {
            token.replace("Bearer ", "")
        }
    }

}
