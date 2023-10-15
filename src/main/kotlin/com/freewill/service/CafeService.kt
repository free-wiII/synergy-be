package com.freewill.service

import com.freewill.dto.param.CafeCreateParam
import com.freewill.entity.Cafe
import com.freewill.repository.CafeRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CafeService(
    private val cafeRepository: CafeRepository
) {
    @Transactional
    fun save(cafeCreateParam: CafeCreateParam) {
        cafeRepository.save(cafeCreateParam.toCafeEntity());
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Cafe = cafeRepository.findById(id).orElseThrow { EntityNotFoundException() }
}