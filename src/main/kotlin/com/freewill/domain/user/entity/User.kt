package com.freewill.domain.user.entity

import com.freewill.domain.user.entity.enums.Provider
import com.freewill.domain.user.entity.enums.Role
import com.freewill.global.audit.AuditEntity
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
    private val id: Long? = null

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private val provider: Provider = provider

    @Column(name = "provider_id", nullable = false)
    private val providerId: String = providerId

    @Column(name = "provider_nickname", nullable = false)
    private val providerNickname: String = providerNickname

    @Column(name = "provider_email")
    private val providerEmail: String? = providerEmail

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    private val role: List<Role> = listOf(Role.ROLE_USER)

    @Embedded
    private var auditEntity: AuditEntity = AuditEntity()

    /* Erase comment after setting Spring Security
     *
    fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return role.stream()
            .map { SimpleGrantedAuthority(it.name) }
            .toList()
    }
     */
}
