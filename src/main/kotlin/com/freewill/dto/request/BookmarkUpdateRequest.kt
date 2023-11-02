package com.freewill.dto.request

import com.freewill.dto.param.BookmarkUpdateParam

data class BookmarkUpdateRequest(
    val bookmarkGroupId: Long
) {
    fun toParam(cafeId: Long): BookmarkUpdateParam {
        return BookmarkUpdateParam(cafeId, bookmarkGroupId)
    }
}
