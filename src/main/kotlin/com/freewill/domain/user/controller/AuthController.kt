package com.freewill.domain.user.controller

import com.freewill.domain.profile.dto.request.ProfileCreateRequest
import com.freewill.domain.user.dto.message.AuthResponseMessage
import com.freewill.domain.user.dto.param.UserRegisterParam
import com.freewill.domain.user.dto.request.SignInRequest
import com.freewill.domain.user.dto.request.SignUpRequest
import com.freewill.domain.user.dto.response.AuthResponse
import com.freewill.domain.user.service.AuthService
import com.freewill.global.common.response.ApiResponse
import com.freewill.global.common.response.ApiResponse.Companion.createSuccessWithData
import com.freewill.security.jwt.dto.JwtToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestPart(value = "image", required = false) image: MultipartFile?,
        @RequestPart(value = "signUpRequest") signUpRequest: SignUpRequest,
    ): ApiResponse<AuthResponse> {
        val jwtToken: JwtToken = authService.register(UserRegisterParam(image, signUpRequest))

        return createSuccessWithData(
            AuthResponseMessage.SUCCESS_SIGN_UP.msg,
            jwtToken.toAuthResponse()
        )
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: SignInRequest): ApiResponse<AuthResponse> {
        return createSuccessWithData(
            AuthResponseMessage.SUCCESS_SIGN_IN.msg,
            authService.signIn(request).toAuthResponse()
        )
    }
}
