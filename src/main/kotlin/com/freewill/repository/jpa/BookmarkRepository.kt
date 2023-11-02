package com.freewill.repository.jpa

import com.freewill.entity.Bookmark
import com.freewill.entity.BookmarkGroup
import com.freewill.entity.Cafe
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkRepository : JpaRepository<Bookmark, Long> {
    fun findByCafeAndBookmarkGroup(cafe: Cafe, bookmarkGroup: BookmarkGroup): Bookmark?
}
