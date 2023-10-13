package com.freewill.security.oauth.util

import com.freewill.entity.User
import org.springframework.stereotype.Component

@Component
class PrincipalUserConverter {
    fun toPrincipalUser(user: User): PrincipalUser {
        return PrincipalUser(user, mutableMapOf("id" to user.id!!))
    }
}
