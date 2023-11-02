package com.freewill.dto.request

import com.freewill.dto.param.BookmarkGroupCreateParam
import com.freewill.entity.BookmarkGroup
import com.freewill.entity.User

data class BookmarkGroupCreateRequest(
    val title: String
) {
    fun toParam(user: User): BookmarkGroupCreateParam {
        return BookmarkGroupCreateParam(title, user)
    }
}
