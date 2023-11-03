package com.freewill.repository.jpa

import com.freewill.entity.BookmarkGroup
import com.freewill.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkGroupRepository : JpaRepository<BookmarkGroup, Long> {
    fun findAllByUser(user: User): List<BookmarkGroup>
}
