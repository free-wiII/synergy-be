package com.freewill.dto.request

import com.freewill.dto.param.CafeCreateParam
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal

data class CafeCreateRequest(
    val name: String,
    val content: String?,
    val address: String,
    val reviewUri: String?,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
) {
    fun toParam(images: List<MultipartFile>?): CafeCreateParam = CafeCreateParam(
        images = images,
        name = name,
        content = content,
        address = address,
        reviewUri = reviewUri,
        latitude = latitude,
        longitude = longitude
    )
}
