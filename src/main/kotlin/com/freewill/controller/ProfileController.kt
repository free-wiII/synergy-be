package com.freewill.controller

import com.freewill.dto.param.ProfileUpdateParam
import com.freewill.dto.request.ProfileUpdateRequest
import com.freewill.dto.response.ProfileDetailResponse
import com.freewill.service.ProfileService
import com.freewill.entity.User
import com.freewill.common.annotation.AuthorizedUser
import com.freewill.common.response.ApiResponse
import com.freewill.common.response.ApiResponse.Companion.createSuccessWithData
import com.freewill.enums.SuccessMessage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/profiles")
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping
    fun searchProfile(@AuthorizedUser user: User): ApiResponse<ProfileDetailResponse> {
        return createSuccessWithData(
            SuccessMessage.SUCCESS_SEARCH_PROFILE.msg,
            profileService.findByUser(user).toProfileDetailResponse()
        )
    }

    @PutMapping
    fun updateProfile(
        @AuthorizedUser user: User,
        @RequestPart(value = "image", required = false) image: MultipartFile?,
        @RequestPart(value = "profileUpdateRequest", required = false) request: ProfileUpdateRequest?
    ): ApiResponse<Void> {
        profileService.update(user, ProfileUpdateParam(image, request?.nickname, request?.email))

        return createSuccessWithData(SuccessMessage.SUCCESS_UPDATE_PROFILE.msg)
    }
}
