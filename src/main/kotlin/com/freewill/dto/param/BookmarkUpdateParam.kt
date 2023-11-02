package com.freewill.dto.param

import com.freewill.entity.BookmarkGroup
import com.freewill.entity.Cafe

data class BookmarkUpdateParam(
    val cafeId: Long,
    val bookmarkGroupId: Long
) {
    fun toCreateParam(cafe: Cafe, bookmarkGroup: BookmarkGroup): BookmarkCreateParam {
        return BookmarkCreateParam(cafe, bookmarkGroup)
    }
}
