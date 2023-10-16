package com.freewill.dto.param

import com.freewill.entity.Cafe
import com.freewill.entity.Guestbook
import com.freewill.entity.User

data class GuestbookCreateParam(
    val user: User,
    val cafe: Cafe,
    val content: String,
) {
    fun toEntity(): Guestbook = Guestbook(
        user = user,
        cafe = cafe,
        content = content
    )
}
