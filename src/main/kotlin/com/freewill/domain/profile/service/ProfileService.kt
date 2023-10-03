package com.freewill.domain.profile.service

import com.freewill.domain.profile.dto.param.ProfileCreateParam
import com.freewill.domain.profile.entity.Profile
import com.freewill.domain.profile.repository.ProfileRepository
import com.freewill.domain.user.entity.User
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileService(
    private val profileRepository: ProfileRepository
) {
    @Transactional
    fun save(param: ProfileCreateParam) {
        profileRepository.save(param.toProfileEntity())
    }

    @Transactional(readOnly = true)
    fun findByUser(user: User): Profile = profileRepository.findByUser(user) ?: throw EntityNotFoundException()
}
