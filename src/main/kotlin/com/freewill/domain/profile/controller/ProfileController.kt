package com.freewill.domain.profile.controller

import com.freewill.domain.profile.dto.message.ProfileResponseMessage
import com.freewill.domain.profile.dto.param.ProfileUpdateParam
import com.freewill.domain.profile.dto.request.ProfileUpdateRequest
import com.freewill.domain.profile.dto.response.ProfileDetailResponse
import com.freewill.domain.profile.service.ProfileService
import com.freewill.domain.user.entity.User
import com.freewill.global.common.annotation.AuthorizedUser
import com.freewill.global.common.response.ApiResponse
import com.freewill.global.common.response.ApiResponse.Companion.createSuccessWithData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/profile")
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping
    fun searchProfile(@AuthorizedUser user: User): ApiResponse<ProfileDetailResponse> {
        return createSuccessWithData(
            ProfileResponseMessage.SUCCESS_SEARCH_PROFILE.msg,
            profileService.findByUser(user).toProfileDetailResponse()
        )
    }

    @PutMapping
    fun updateProfile(
        @AuthorizedUser user: User,
        @RequestPart(value = "image", required = false) image: MultipartFile?,
        @RequestPart(value = "profileUpdateRequest", required = false) request: ProfileUpdateRequest?
    ): ApiResponse<Long> {
        profileService.update(user, ProfileUpdateParam(image, request?.nickname))

        return createSuccessWithData(ProfileResponseMessage.SUCCESS_UPDATE_PROFILE.msg)
    }
}
