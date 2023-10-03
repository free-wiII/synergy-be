package com.freewill.domain.profile.entity

import com.freewill.domain.profile.dto.param.ProfileUpdateParam
import com.freewill.domain.profile.dto.request.ProfileUpdateRequest
import com.freewill.domain.profile.dto.response.ProfileDetailResponse
import com.freewill.domain.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "profiles")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
class Profile(
    user: User,
    imageUri: String?,
    nickname: String,
    email: String,
) {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "image_uri")
    private var imageUri: String? = imageUri

    @Column(name = "nickname", nullable = false)
    private var nickname: String = nickname

    @Column(name = "email")
    private var email: String = email

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private val user: User = user

    fun update(request: ProfileUpdateParam) {
        request.nickname?.let { this.nickname = it }
    }

    fun toProfileDetailResponse() = ProfileDetailResponse(
        imageUri = imageUri,
        nickname = nickname,
        email = email
    )
}
