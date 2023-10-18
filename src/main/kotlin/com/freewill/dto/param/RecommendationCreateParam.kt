package com.freewill.dto.param

import com.freewill.entity.Cafe
import com.freewill.entity.Recommendation
import com.freewill.entity.User

data class RecommendationCreateParam(
    val cafe: Cafe,
    val user: User
) {
    fun toEntity(): Recommendation = Recommendation(cafe, user)
}
