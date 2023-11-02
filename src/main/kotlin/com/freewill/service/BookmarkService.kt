package com.freewill.service

import com.freewill.dto.param.BookmarkCreateParam
import com.freewill.dto.param.BookmarkUpdateParam
import com.freewill.dto.response.BookmarkCafeListResponse
import com.freewill.dto.response.BookmarkCafeResponse
import com.freewill.entity.Bookmark
import com.freewill.entity.BookmarkGroup
import com.freewill.entity.Cafe
import com.freewill.entity.User
import com.freewill.repository.jpa.BookmarkRepository
import com.freewill.repository.querydsl.BookmarkQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Objects.isNull

@Service
class BookmarkService(
    private val bookmarkGroupService: BookmarkGroupService,
    private val bookmarkRepository: BookmarkRepository,
    private val bookmarkQueryRepository: BookmarkQueryRepository
) {
    @Transactional
    fun update(param: BookmarkUpdateParam) {
        val bookmarkGroup: BookmarkGroup = bookmarkGroupService.findById(param.bookmarkGroupId)

        val bookmark: Bookmark = findByCafeAndBookmarkGroup(param.cafe, bookmarkGroup)
            ?: save(param.toCreateParam(bookmarkGroup))

        bookmark.updateFlag()
    }

    @Transactional
    fun save(param: BookmarkCreateParam): Bookmark = bookmarkRepository.save(param.toEntity())

    @Transactional(readOnly = true)
    fun findByCafeAndBookmarkGroup(cafe: Cafe, bookmarkGroup: BookmarkGroup): Bookmark? {
        return bookmarkRepository.findByCafeAndBookmarkGroup(cafe, bookmarkGroup)
    }

    @Transactional(readOnly = true)
    fun findAllByBookmarkGroupId(bookmarkGroupId: Long): BookmarkCafeListResponse {
        val bookmarkCafes: List<BookmarkCafeResponse> = bookmarkQueryRepository.findByGroupId(bookmarkGroupId)

        return BookmarkCafeListResponse(bookmarkCafes, bookmarkCafes.size)
    }

    @Transactional(readOnly = true)
    fun existsByCafeAndUser(cafe: Cafe, user: User?): Boolean {
        return if (isNull(user)) false else bookmarkQueryRepository.existsByCafeAndUser(cafe, user!!)
    }
}
