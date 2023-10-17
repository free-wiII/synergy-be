package com.freewill.entity

import com.freewill.dto.response.CafeDetailResponse
import com.freewill.dto.response.GuestbookSimpleResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
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
    reviewUri: String?,
    latitude: BigDecimal,
    longitude: BigDecimal,
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

    @Column(name = "review_uri")
    private var reviewUri: String? = reviewUri

    @Column(name = "latitude", nullable = false)
    private var latitude: BigDecimal = latitude

    @Column(name = "longitude", nullable = false)
    private var longitude: BigDecimal = longitude

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cafe")
    private val guestbooks: MutableList<Guestbook> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cafe")
    private val images: MutableList<CafeImage> = mutableListOf()

    fun toCafeDetailResponse(guestbooks: List<GuestbookSimpleResponse>) = CafeDetailResponse(
        imageUris = images.stream().map { it.uri }.toList(),
        name = name,
        content = content,
        address = address,
        reviewUri = reviewUri,
        guestbooks = guestbooks
    )
}
