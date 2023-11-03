package com.freewill.repository.querydsl

import com.freewill.dto.response.BookmarkCafeResponse
import com.freewill.entity.*
import com.freewill.entity.QBookmark.bookmark
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.Objects.nonNull

@Repository
class BookmarkQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findByGroupId(id: Long): List<BookmarkCafeResponse> {
        return queryFactory.select(
            Projections.fields(
                BookmarkCafeResponse::class.java,
                QCafe.cafe.name,
                QCafe.cafe.address,
                ExpressionUtils.`as`(
                    JPAExpressions.select(QCafeImage.cafeImage.uri)
                        .from(QCafeImage.cafeImage)
                        .where(QCafeImage.cafeImage.cafe.eq(QCafe.cafe))
                        .limit(3),
                    "imageUris"
                )
            )
        )
            .from(QCafe.cafe, QBookmark.bookmark)
            .where(QBookmark.bookmark.bookmarkGroup.id.eq(id), QBookmark.bookmark.cafe.eq(QCafe.cafe))
            .fetch();
    }

    fun existsByCafeAndUser(cafe: Cafe, user: User): Boolean {
        return nonNull(
            queryFactory
                .selectFrom(bookmark)
                .where(bookmark.cafe.eq(cafe), bookmark.bookmarkGroup.user.eq(user))
                .fetchOne()
        );
    }
}
