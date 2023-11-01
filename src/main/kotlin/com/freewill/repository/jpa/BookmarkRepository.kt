package com.freewill.repository.jpa

import com.freewill.entity.Bookmark
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkRepository : JpaRepository<Bookmark, Long> {
}
