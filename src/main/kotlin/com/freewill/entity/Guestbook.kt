package com.freewill.domain.guestbook.entity

import com.freewill.domain.cafe.entity.Cafe
import com.freewill.domain.review.entity.Review
import com.freewill.entity.User
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

@Entity
@Table(name = "guestbooks")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
class Guestbook(
    user: User,
    cafe: Cafe,
    content: String,
    recommendation: Boolean,
    reviews: MutableList<Review>,
) {
    @Id
    @Column(name = "guestbook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "content", nullable = false)
    private var content: String = content

    @Column(name = "recommendation")
    private var recommendation: Boolean = recommendation

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private val user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", referencedColumnName = "cafe_id")
    private var cafe: Cafe = cafe

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "guestbook")
    private var reviews: MutableList<Review> = reviews
}
