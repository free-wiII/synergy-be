package com.freewill.dto.request

import com.freewill.dto.param.GuestbookCreateParam
import com.freewill.entity.Cafe
import com.freewill.entity.User

data class GuestbookCreateRequest(
    val cafeId: Long,
    val content: String,
    val reviews: MutableList<ReviewCreateRequest>
) {
    fun toParam(user: User, cafe: Cafe): GuestbookCreateParam = GuestbookCreateParam(
        user = user,
        cafe = cafe,
        content = content,
        reviews = reviews
    )
}
