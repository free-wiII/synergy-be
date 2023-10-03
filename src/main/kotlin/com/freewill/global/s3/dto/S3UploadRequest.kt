package com.freewill.global.s3.dto

import org.springframework.web.multipart.MultipartFile

data class S3UploadRequest(val image: MultipartFile, val userId: Long)
