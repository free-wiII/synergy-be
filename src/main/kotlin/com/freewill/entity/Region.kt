package com.freewill.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "regions")
@EntityListeners(AuditingEntityListener::class)
class Region(
    si: String,
    gu: String,
) {
    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "si", nullable = false)
    private var si: String = si

    @Column(name = "gu", nullable = false)
    private var gu: String = gu
}
