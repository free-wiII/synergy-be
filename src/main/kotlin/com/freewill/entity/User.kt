package com.freewill.entity

import com.freewill.dto.param.OAuth2Param
import com.freewill.enums.Provider
import com.freewill.enums.Role
import com.freewill.entity.audit.AuditEntity
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "users")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
class User(
    provider: Provider,
    providerId: String,
    providerNickname: String,
    providerEmail: String?,
) {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    val provider: Provider = provider

    @Column(name = "provider_id", nullable = false)
    val providerId: String = providerId

    @Column(name = "provider_nickname", nullable = false)
    val providerNickname: String = providerNickname

    @Column(name = "provider_email")
    val providerEmail: String? = providerEmail

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val role: List<Role> = listOf(Role.ROLE_USER)

    @Embedded
    var auditEntity: AuditEntity = AuditEntity()

    constructor(oAuth2Param: OAuth2Param) : this(
        provider = oAuth2Param.provider,
        providerId = oAuth2Param.providerId,
        providerNickname = oAuth2Param.providerNickname,
        providerEmail = oAuth2Param.providerEmail
    )
}
