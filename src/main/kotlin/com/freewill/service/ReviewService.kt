package com.freewill.service

import com.freewill.dto.param.ReviewCreateParam
import com.freewill.repository.jpa.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository
) {
    fun saveAll(params: List<ReviewCreateParam>) {
        reviewRepository.saveAll(params.map { it.toEntity() }.toList())
    }

    fun save(param: ReviewCreateParam) {
        reviewRepository.save(param.toEntity())
    }
}
