package com.freewill.dto.param

import com.freewill.entity.Profile
import com.freewill.entity.User

data class ProfileCreateParam(
    val user: User,
    val nickname: String,
    val email: String?
) {
    fun toProfileEntity(): Profile = Profile(user, null, nickname, email)
}
