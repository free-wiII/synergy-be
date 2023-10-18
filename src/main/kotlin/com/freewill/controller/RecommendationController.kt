package com.freewill.controller

import com.freewill.common.annotation.AuthorizedUser
import com.freewill.common.response.ApiResponse
import com.freewill.dto.param.RecommendationUpdateParam
import com.freewill.entity.User
import com.freewill.enums.ResponseMessage
import com.freewill.service.RecommendationService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cafes/{id}")
class RecommendationController(
    private val recommendationService: RecommendationService
) {
    @PutMapping("/recommendations")
    fun recommendCafe(@PathVariable id: Long, @AuthorizedUser user: User): ApiResponse<Void> {
        recommendationService.update(RecommendationUpdateParam(user, id))

        return ApiResponse.createSuccess(ResponseMessage.SUCCESS_UPDATE_RECOMMENDATION.msg)
    }
}