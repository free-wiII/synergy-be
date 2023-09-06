package com.freewill.domain.user.controller

import com.freewill.domain.user.dto.message.AuthResponseMessage
import com.freewill.domain.user.dto.request.AuthRequest
import com.freewill.domain.user.dto.response.AuthResponse
import com.freewill.domain.user.service.AuthService
import com.freewill.global.common.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/sign-up/apple")
    fun signUpByApple(@RequestBody authRequest: AuthRequest): ApiResponse<AuthResponse> {
        return ApiResponse.createSuccessWithData(
            AuthResponseMessage.SUCCESS_AUTHORIZE.msg,
            authService.signUpByApple(authRequest).toAuthResponse()
        )
    }

//    @PostMapping("/sign-up/kakao")
//    fun signUpByKakao(@RequestBody authRequest: AuthRequest) {
//        return
//    }
}
