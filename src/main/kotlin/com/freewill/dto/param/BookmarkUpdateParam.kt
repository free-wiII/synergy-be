package com.freewill.dto.param

import com.freewill.entity.BookmarkGroup
import com.freewill.entity.Cafe

data class BookmarkUpdateParam(
    val cafe: Cafe,
    val bookmarkGroupId: Long
) {
    fun toCreateParam(bookmarkGroup: BookmarkGroup): BookmarkCreateParam {
        return BookmarkCreateParam(cafe, bookmarkGroup)
    }
}
