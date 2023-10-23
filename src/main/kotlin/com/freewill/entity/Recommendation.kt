package com.freewill.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "recommendation")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
class Recommendation(
    cafe: Cafe,
    user: User
) {
    @Id
    @Column(name = "recommendation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "flag", nullable = false)
    var flag: Boolean = false

    @ManyToOne
    @JoinColumn(name = "cafe_id", referencedColumnName = "cafe_id")
    val cafe: Cafe = cafe

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    val user: User = user

    fun updateFlag() {
        flag = !flag
    }
}
