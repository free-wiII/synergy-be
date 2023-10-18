package com.freewill.dto.param

import com.freewill.entity.Cafe
import com.freewill.entity.Recommendation
import com.freewill.entity.User

data class RecommendationUpdateParam(
    val user: User,
    val cafeId: Long
) {
    fun toCreateParam(cafe: Cafe): RecommendationCreateParam = RecommendationCreateParam(cafe, user)
}
