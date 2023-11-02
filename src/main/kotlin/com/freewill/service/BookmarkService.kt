package com.freewill.service

import com.freewill.dto.param.BookmarkCreateParam
import com.freewill.dto.param.BookmarkUpdateParam
import com.freewill.entity.Bookmark
import com.freewill.entity.BookmarkGroup
import com.freewill.entity.Cafe
import com.freewill.repository.jpa.BookmarkRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkService(
    private val cafeService: CafeService,
    private val bookmarkGroupService: BookmarkGroupService,
    private val bookmarkRepository: BookmarkRepository
) {
    @Transactional
    fun update(param: BookmarkUpdateParam) {
        val cafe: Cafe = cafeService.findById(param.cafeId)
        val bookmarkGroup: BookmarkGroup = bookmarkGroupService.findById(param.bookmarkGroupId)

        val bookmark: Bookmark = findByCafeAndBookmarkGroup(cafe, bookmarkGroup)
            ?: save(param.toCreateParam(cafe, bookmarkGroup))

        bookmark.updateFlag()
    }

    @Transactional
    fun save(param: BookmarkCreateParam): Bookmark = bookmarkRepository.save(param.toEntity())

    @Transactional(readOnly = true)
    fun findByCafeAndBookmarkGroup(cafe: Cafe, bookmarkGroup: BookmarkGroup): Bookmark? {
        return bookmarkRepository.findByCafeAndBookmarkGroup(cafe, bookmarkGroup)
    }
}
