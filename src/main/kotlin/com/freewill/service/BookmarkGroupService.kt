package com.freewill.service

import com.freewill.dto.param.BookmarkGroupCreateParam
import com.freewill.dto.response.BookmarkGroupListResponse
import com.freewill.entity.BookmarkGroup
import com.freewill.entity.User
import com.freewill.repository.jpa.BookmarkGroupRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkGroupService(
    private val bookmarkGroupRepository: BookmarkGroupRepository
) {
    @Transactional
    fun save(param: BookmarkGroupCreateParam) {
        bookmarkGroupRepository.save(param.toEntity())
    }

    @Transactional(readOnly = true)
    fun findAll(user: User): BookmarkGroupListResponse {
        val bookmarkGroups = findAllByUser(user)

        return BookmarkGroupListResponse(
            bookmarkGroups = bookmarkGroups.map { it.title },
            totalCount = bookmarkGroups.size
        )
    }



    fun findById(id: Long): BookmarkGroup {
        return bookmarkGroupRepository.findById(id).orElseThrow { EntityNotFoundException() }
    }

    fun findAllByUser(user: User): List<BookmarkGroup> = bookmarkGroupRepository.findAllByUser(user)

}
