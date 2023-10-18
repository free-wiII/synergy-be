package com.freewill.repository.querydsl

import com.freewill.dto.response.QReviewAvgResponse
import com.freewill.dto.response.ReviewAvgResponse
import com.freewill.entity.QReview.review
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ReviewQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findReviewAvg(cafeId: Long): List<ReviewAvgResponse> {
        return queryFactory
            .select(QReviewAvgResponse(review.type, review.point.avg()))
            .where(eqCafe(cafeId))
            .fetch()
    }

    fun eqCafe(cafeId: Long): BooleanExpression {
        return review.guestbook.cafe.id.eq(cafeId)
    }
}
