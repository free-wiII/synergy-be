package com.freewill.repository.querydsl

import com.freewill.dto.response.BookmarkCafeResponse
import com.freewill.dto.response.QBookmarkCafeResponse
import com.freewill.entity.QBookmark.bookmark
import com.freewill.entity.QBookmarkGroup.bookmarkGroup
import com.freewill.entity.QCafe.cafe
import com.freewill.entity.QCafeImage.cafeImage
import com.querydsl.core.types.Expression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BookmarkGroupQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
//    fun findByGroupId(id: Long): List<BookmarkCafeResponse> {
//        return queryFactory.select(
//            bookmark.cafe.name,
//            bookmark.cafe.address,
//            bookmark.cafe.images)
//            .from(bookmark)
//            .fetch();
//    }
}
