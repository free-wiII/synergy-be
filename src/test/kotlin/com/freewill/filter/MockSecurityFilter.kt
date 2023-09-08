package com.freewill.filter

import com.freewill.domain.user.entity.User
import com.freewill.domain.user.entity.enums.Provider
import com.freewill.security.oauth.util.PrincipalUser
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.io.IOException
import java.lang.reflect.Field

class MockSecurityFilter : Filter {
    override fun init(filterConfig: FilterConfig) {}

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse?,
        chain: FilterChain
    ) {
        val context: SecurityContext = SecurityContextHolder.getContext()
        val principalUser = PrincipalUser(createMember(), mutableMapOf(), mutableListOf())
        val authentication = UsernamePasswordAuthenticationToken(
            principalUser, principalUser.password,
            principalUser.authorities
        )

        context.authentication = authentication
        chain.doFilter(request, response)
    }

    override fun destroy() {
        SecurityContextHolder.clearContext()
    }

    fun getFilters(mockHttpServletRequest: MockHttpServletRequest?) {}

    companion object {
        private const val name = "Test Name"
        private const val email = "Test@test.com"

        private fun createMember(): User {
            val user: User = User(Provider.GOOGLE, "1234", name, email)
            val userClass: Class<User> = User::class.java
            try {
                val id: Field = userClass.getDeclaredField("id")
                id.isAccessible = true
                id.set(user, 1L)
            } catch (e: NoSuchFieldException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }
            return user
        }
    }
}
