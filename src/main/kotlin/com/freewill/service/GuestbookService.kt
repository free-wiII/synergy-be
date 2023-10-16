package com.freewill.service

import com.freewill.dto.param.GuestbookCreateParam
import com.freewill.entity.Guestbook
import com.freewill.repository.jpa.GuestbookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GuestbookService(
    private val guestbookRepository: GuestbookRepository,
    private val reviewService: ReviewService
) {
    @Transactional
    fun save(param: GuestbookCreateParam) = guestbookRepository.save(param.toEntity())
}
