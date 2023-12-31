package com.freewill.service

import com.freewill.dto.param.OAuth2Param
import com.freewill.entity.User
import com.freewill.repository.jpa.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional
    fun save(oAuth2Param: OAuth2Param): User {
        return userRepository.save(User(oAuth2Param))
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): User = userRepository.findById(id).orElseThrow { EntityNotFoundException() }

    @Transactional(readOnly = true)
    fun findByProviderId(providerId: String): User? = userRepository.findByProviderId(providerId)
}
