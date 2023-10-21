package com.freewill.dto.param

import com.freewill.entity.Cafe
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal

data class CafeCreateParam(
    val images: List<MultipartFile>?,
    val name: String,
    val content: String?,
    val address: String,
    val reviewUri: String?,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
) {
    fun toCafeEntity(): Cafe = Cafe(
        name = name,
        content = content,
        address = address,
        reviewUri = reviewUri,
        latitude = latitude,
        longitude = longitude
    )
}
