package com.freewill.dto.param

import org.springframework.web.multipart.MultipartFile

data class ProfileUpdateParam(
    val image: MultipartFile?,
    val nickname: String?,
    val email: String?
)
