package com.freewill.controller

import com.freewill.common.annotation.AuthorizedUser
import com.freewill.common.response.ApiResponse
import com.freewill.dto.param.RecommendationUpdateParam
import com.freewill.entity.Cafe
import com.freewill.entity.User
import com.freewill.enums.SuccessMessage
import com.freewill.service.CafeService
import com.freewill.service.RecommendationService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cafes/{id}")
class RecommendationController(
    private val recommendationService: RecommendationService,
    private val cafeService: CafeService
) {
    @PutMapping("/recommendations")
    fun recommendCafe(@PathVariable id: Long, @AuthorizedUser user: User): ApiResponse<Void> {
        val cafe: Cafe = cafeService.findById(id)
        recommendationService.update(RecommendationUpdateParam(user, cafe))

        return ApiResponse.createSuccess(SuccessMessage.SUCCESS_UPDATE_RECOMMENDATION.msg)
    }
}
