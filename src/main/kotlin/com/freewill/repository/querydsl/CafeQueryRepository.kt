package com.freewill.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CafeQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
}
