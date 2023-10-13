package com.freewill.domain.user.dto.param

import com.freewill.domain.user.dto.request.SignUpRequest
import org.springframework.web.multipart.MultipartFile

data class UserRegisterParam(
    val image: MultipartFile?,
    val signUpRequest: SignUpRequest
)
