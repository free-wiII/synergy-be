package com.freewill.domain.profile.dto.param

import com.freewill.domain.profile.entity.Profile
import com.freewill.domain.user.entity.User

data class ProfileCreateParam(
    val imageUri: String?,
    val user: User,
    val nickname: String,
    val email: String?
) {
    fun toProfileEntity(): Profile = Profile(user, imageUri, nickname, email)
}
