package com.freewill.service

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.freewill.config.S3Config
import com.freewill.dto.request.UploadPresignedUrlRequest
import com.freewill.dto.response.PresignedUrlListResponse
import com.freewill.dto.response.PresignedUrlResponse
import com.freewill.enums.DomainType
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Date

@Service
class PresignedUrlService(
    private val s3Config: S3Config
) {
    fun getUploadPresignedUrls(dto: UploadPresignedUrlRequest): PresignedUrlListResponse {
        val presignedUrls: List<PresignedUrlResponse> = dto.imageNames.map { getUploadPresignedUrl(dto.domain, it) }

        return PresignedUrlListResponse(presignedUrls, presignedUrls.size)
    }

    fun getUploadPresignedUrl(domain: DomainType, imageName: String): PresignedUrlResponse {
        val filename: String = encodeFileName(imageName)
        val path: String = "image/${domain.toLowerCase()}/$filename"

        val presignedUrl: String = getPresignedUrl(
            path = path,
            method = HttpMethod.PUT,
            expiration = getExpiration()
        )

        return PresignedUrlResponse(presignedUrl, filename)
    }

    private fun getPresignedUrl(path: String, method: HttpMethod, expiration: Date): String {
        return s3Config.amazonS3Client().generatePresignedUrl(
            getGeneratePresignedUrlRequest(
                path = path,
                method = HttpMethod.PUT,
                expiration = getExpiration()
            )
        ).toString()
    }

    private fun getGeneratePresignedUrlRequest(
        path: String,
        method: HttpMethod,
        expiration: Date
    ): GeneratePresignedUrlRequest {
        return GeneratePresignedUrlRequest(s3Config.bucketName, path)
            .withMethod(method)
            .withExpiration(expiration)
    }

    private fun encodeFileName(imageName: String): String {
        return "$imageName-${LocalDate.now()}"
    }

    private fun getExpiration(): Date {
        val expiration = Date()
        expiration.time += (3 * 60 * 1000)

        return expiration
    }
}
