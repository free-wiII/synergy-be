package com.freewill.security.oauth.util

import com.freewill.domain.user.entity.User
import org.springframework.stereotype.Component

@Component
class PrincipalUserConverter {
    fun toPrincipalUser(user: User): PrincipalUser {
        return PrincipalUser(user, mutableMapOf("id" to user.id!!))
    }
}
