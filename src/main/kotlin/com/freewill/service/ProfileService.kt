package com.freewill.service

import com.freewill.dto.param.ProfileCreateParam
import com.freewill.dto.param.ProfileUpdateParam
import com.freewill.entity.Profile
import com.freewill.repository.jpa.ProfileRepository
import com.freewill.entity.User
import com.freewill.s3.S3Uploader
import com.freewill.s3.dto.S3UploadRequest
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val s3Uploader: S3Uploader
) {
    @Transactional
    fun save(param: ProfileCreateParam) {
        profileRepository.save(param.toProfileEntity())
    }

    @Transactional
    fun update(user: User, param: ProfileUpdateParam) {
        val profile: Profile = findByUser(user)

        param.image?.let {
            s3Uploader.upload(S3UploadRequest(param.image, user.id!!))
        }

        profile.update(param)
    }

    @Transactional(readOnly = true)
    fun findByUser(user: User): Profile = profileRepository.findByUser(user) ?: throw EntityNotFoundException()
}
