package com.freewill.dto.param

import com.freewill.entity.Cafe
import com.freewill.entity.User

data class RecommendationUpdateParam(
    val user: User,
    val cafe: Cafe
) {
    fun toCreateParam(): RecommendationCreateParam = RecommendationCreateParam(cafe, user)
}
