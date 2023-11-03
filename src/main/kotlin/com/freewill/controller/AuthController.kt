package com.freewill.controller

import com.freewill.enums.SuccessMessage
import com.freewill.dto.request.SignInRequest
import com.freewill.dto.request.SignUpRequest
import com.freewill.dto.response.AuthResponse
import com.freewill.service.AuthService
import com.freewill.common.response.ApiResponse
import com.freewill.common.response.ApiResponse.Companion.createSuccessWithData
import com.freewill.security.jwt.dto.JwtToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ApiResponse<AuthResponse> {
        val jwtToken: JwtToken = authService.signUp(signUpRequest)

        return createSuccessWithData(
            SuccessMessage.SUCCESS_SIGN_UP.msg,
            jwtToken.toAuthResponse()
        )
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: SignInRequest): ApiResponse<AuthResponse> {
        return createSuccessWithData(
            SuccessMessage.SUCCESS_SIGN_IN.msg,
            authService.signIn(request).toAuthResponse()
        )
    }
}
