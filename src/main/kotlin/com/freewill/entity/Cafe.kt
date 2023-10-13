package com.freewill.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal

@Entity
@Table(name = "cafes")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
class Cafe(
    name: String,
    content: String?,
    address: String,
    imageUri: String?,
    reviewUri: String?,
    latitude: BigDecimal,
    longitude: BigDecimal,
    region: Region,
) {
    @Id
    @Column(name = "cafe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "name")
    private var name: String = name

    @Column(name = "content")
    private var content: String? = content

    @Column(name = "address", nullable = false)
    private var address: String = address

    @Column(name = "image_uri")
    private var imageUri: String? = imageUri

    @Column(name = "review_uri")
    private var reviewUri: String? = reviewUri

    @Column(name = "latitude", nullable = false)
    private var latitude: BigDecimal = latitude

    @Column(name = "longitude", nullable = false)
    private var longitude: BigDecimal = longitude

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cafe")
    private val guestbooks: MutableList<Guestbook> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    private var region: Region = region
}
