package com.freewill.dto.param

import com.freewill.dto.request.SignUpRequest
import org.springframework.web.multipart.MultipartFile

data class UserRegisterParam(
    val image: MultipartFile?,
    val signUpRequest: SignUpRequest
)
