package com.freewill.repository.jpa

import com.freewill.entity.BookmarkGroup
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkGroupRepository : JpaRepository<BookmarkGroup, Long> {
}
