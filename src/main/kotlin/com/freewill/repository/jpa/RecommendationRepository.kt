package com.freewill.repository.jpa

import com.freewill.entity.Cafe
import com.freewill.entity.Recommendation
import com.freewill.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecommendationRepository : JpaRepository<Recommendation, Long> {
    fun findByUserAndCafe(user: User, cafe: Cafe): Recommendation?
    fun existsByCafeAndUser(cafe: Cafe, user: User): Boolean
}
