package com.freewill.service

import com.freewill.dto.param.CafeCreateParam
import com.freewill.dto.response.CafeDetailResponse
import com.freewill.dto.response.GuestbookSimpleResponse
import com.freewill.dto.response.ReviewAvgResponse
import com.freewill.entity.Cafe
import com.freewill.repository.jpa.CafeRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CafeService(
    private val cafeRepository: CafeRepository,
    private val guestbookService: GuestbookService,
    private val reviewService: ReviewService
) {
    @Transactional
    fun save(cafeCreateParam: CafeCreateParam) {
        cafeRepository.save(cafeCreateParam.toCafeEntity());
    }

    @Transactional(readOnly = true)
    fun findCafeDetail(id: Long): CafeDetailResponse {
        val cafe: Cafe = cafeRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }
        val guestbooks = guestbookService.findByCafe(id, false).guestbooks;
        val reviews: List<ReviewAvgResponse> = reviewService.findReviewAvg(id);

        return cafe.toCafeDetailResponse(reviews, guestbooks)
    }

    fun findById(id: Long): Cafe = cafeRepository.findById(id)
        .orElseThrow { EntityNotFoundException() }
}
