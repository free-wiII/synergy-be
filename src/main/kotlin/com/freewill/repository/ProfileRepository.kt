package com.freewill.domain.profile.repository

import com.freewill.domain.profile.entity.Profile
import com.freewill.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByUser(user: User): Profile?
}
