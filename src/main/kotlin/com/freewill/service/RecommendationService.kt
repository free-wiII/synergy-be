package com.freewill.service

import com.freewill.dto.param.RecommendationCreateParam
import com.freewill.dto.param.RecommendationUpdateParam
import com.freewill.entity.Cafe
import com.freewill.entity.Recommendation
import com.freewill.entity.User
import com.freewill.repository.jpa.RecommendationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecommendationService(
    private val cafeService: CafeService,
    private val recommendationRepository: RecommendationRepository
) {
    @Transactional
    fun update(param: RecommendationUpdateParam) {
        val cafe: Cafe = cafeService.findById(param.cafeId)
        val recommendation: Recommendation = findByUserAndCafe(param.user, cafe) ?: save(param.toCreateParam(cafe))

        recommendation.updateFlag()
    }

    @Transactional
    fun save(param: RecommendationCreateParam): Recommendation = recommendationRepository.save(param.toEntity())

    @Transactional(readOnly = true)
    fun findByUserAndCafe(user: User, cafe: Cafe): Recommendation? {
        return recommendationRepository.findByUserAndCafe(user, cafe)
    }
}
