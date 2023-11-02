package com.freewill.dto.request

import com.freewill.dto.param.BookmarkUpdateParam
import com.freewill.entity.Cafe

data class BookmarkUpdateRequest(
    val bookmarkGroupId: Long
) {
    fun toParam(cafe: Cafe): BookmarkUpdateParam {
        return BookmarkUpdateParam(cafe, bookmarkGroupId)
    }
}
