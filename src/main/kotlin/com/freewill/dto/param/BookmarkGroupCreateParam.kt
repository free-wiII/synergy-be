package com.freewill.dto.param

import com.freewill.entity.BookmarkGroup
import com.freewill.entity.User

data class BookmarkGroupCreateParam(
    val title: String,
    val user: User
) {
    fun toEntity(): BookmarkGroup {
        return BookmarkGroup(title, user)
    }
}
