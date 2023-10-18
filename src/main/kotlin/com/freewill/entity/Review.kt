package com.freewill.entity

import com.freewill.enums.ReviewType
import jakarta.persistence.*

@Entity
@Table(name = "reviews")
class Review(
    guestbook: Guestbook,
    type: ReviewType,
    point: Double,
) {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private val type: ReviewType = type

    @Column(name = "point", nullable = false)
    private var point: Double = point

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestbook_id", referencedColumnName = "guestbook_id")
    private var guestbook: Guestbook = guestbook
}
