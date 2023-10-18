package com.freewill.service

import com.freewill.dto.param.GuestbookCreateParam
import com.freewill.dto.response.GuestbookListResponse
import com.freewill.dto.response.GuestbookSimpleResponse
import com.freewill.entity.Guestbook
import com.freewill.repository.jpa.GuestbookRepository
import com.freewill.repository.querydsl.GuestbookQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GuestbookService(
    private val guestbookRepository: GuestbookRepository,
    private val guestbookQueryRepository: GuestbookQueryRepository,
    private val reviewService: ReviewService
) {
    @Transactional
    fun save(param: GuestbookCreateParam) {
        val guestbook: Guestbook = guestbookRepository.save(param.toEntity())

        reviewService.saveAll(param.reviews.map { it.toParam(guestbook) }.toList())
    }

    @Transactional(readOnly = true)
    fun findByCafe(cafeId: Long, listFlag: Boolean): GuestbookListResponse {
        val guestbooks: List<GuestbookSimpleResponse> = guestbookQueryRepository.findByCafeId(cafeId, listFlag)
        return GuestbookListResponse(guestbooks, guestbooks.size)
    }
}
