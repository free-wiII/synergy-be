package com.freewill.repository.querydsl

import com.freewill.dto.response.GuestbookSimpleResponse
import com.freewill.dto.response.QGuestbookSimpleResponse
import com.freewill.entity.QGuestbook.guestbook
import com.freewill.entity.QProfile.profile
import com.freewill.entity.QUser.user
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class GuestbookQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findByCafeId(cafeId: Long, listFlag: Boolean): List<GuestbookSimpleResponse> {
        val query = queryFactory
            .select(QGuestbookSimpleResponse(profile.nickname, guestbook.content))
            .from(profile, guestbook)
            .where(eqCafe(cafeId), eqUser())

        if (!listFlag) {
            query.offset(0).limit(3)
        }

        return query.fetch()
    }

    fun eqCafe(cafeId: Long): BooleanExpression = guestbook.cafe.id.eq(cafeId)

    fun eqUser(): BooleanExpression = profile.user.id.eq(user.id)
}
