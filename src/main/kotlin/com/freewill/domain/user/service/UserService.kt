package com.freewill.domain.user.service

import com.freewill.domain.user.dto.param.OAuth2Param
import com.freewill.domain.user.entity.User
import com.freewill.domain.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional
    fun save(oAuth2Param: OAuth2Param): User {
        return User(oAuth2Param)
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): User = userRepository.findById(id).orElseThrow { EntityNotFoundException() }

    @Transactional(readOnly = true)
    fun findByProviderId(providerId: String): User? = userRepository.findByProviderId(providerId)
}
