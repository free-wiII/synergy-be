package com.freewill.dto.param

import com.freewill.entity.Bookmark
import com.freewill.entity.BookmarkGroup
import com.freewill.entity.Cafe

data class BookmarkCreateParam(
    val cafe: Cafe,
    val bookmarkGroup: BookmarkGroup
) {
    fun toEntity(): Bookmark = Bookmark(cafe, bookmarkGroup)
}
