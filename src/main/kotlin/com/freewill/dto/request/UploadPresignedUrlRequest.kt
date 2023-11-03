package com.freewill.dto.request

import com.freewill.enums.DomainType

data class UploadPresignedUrlRequest(
    val domain: DomainType,
    val imageNames: List<String>
)
