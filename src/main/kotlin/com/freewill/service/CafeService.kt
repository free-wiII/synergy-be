package com.freewill.service

import com.freewill.dto.param.CafeCreateParam
import com.freewill.dto.response.CafeDetailResponse
import com.freewill.dto.response.ReviewAvgResponse
import com.freewill.entity.Cafe
import com.freewill.entity.User
import com.freewill.repository.jpa.CafeRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CafeService(
    private val cafeRepository: CafeRepository,
    private val guestbookService: GuestbookService,
    private val reviewService: ReviewService,
    private val recommendationService: RecommendationService,
    private val bookmarkService: BookmarkService
) {
    @Transactional
    fun save(cafeCreateParam: CafeCreateParam) {
        cafeRepository.save(cafeCreateParam.toCafeEntity());
    }

    @Transactional(readOnly = true)
    fun findCafeDetail(id: Long, user: User?): CafeDetailResponse {
        val cafe: Cafe = findById(id)
        val guestbooks = guestbookService.findByCafe(id, false).guestbooks;
        val reviews: List<ReviewAvgResponse> = reviewService.findReviewAvg(id);
        val recommendationFlag: Boolean = recommendationService.existsByCafeAndUser(cafe, user)
        val bookmarkFlag: Boolean = bookmarkService.existsByCafeAndUser(cafe, user)

        return cafe.toCafeDetailResponse(reviews, guestbooks, recommendationFlag, bookmarkFlag)
    }

    fun findById(id: Long): Cafe = cafeRepository.findById(id)
        .orElseThrow { EntityNotFoundException() }
}
