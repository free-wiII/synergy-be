package com.freewill.service

import com.freewill.dto.param.ReviewCreateParam
import com.freewill.dto.response.ReviewAvgResponse
import com.freewill.repository.jpa.ReviewRepository
import com.freewill.repository.querydsl.ReviewQueryRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val reviewQueryRepository: ReviewQueryRepository
) {
    fun saveAll(params: List<ReviewCreateParam>) {
        reviewRepository.saveAll(params.map { it.toEntity() }.toList())
    }

    fun save(param: ReviewCreateParam) {
        reviewRepository.save(param.toEntity())
    }

    fun findReviewAvg(cafeId: Long): List<ReviewAvgResponse> {
        return reviewQueryRepository.findReviewAvg(cafeId)
    }
}
