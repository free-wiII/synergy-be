package com.freewill.entity

import com.freewill.entity.Guestbook
import com.freewill.entity.Question
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "reviews")
class Review(
    question: Question,
    guestbook: Guestbook,
    point: Double,
) {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "point")
    private var point: Double = point

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestbook_id", referencedColumnName = "guestbook_id")
    private var guestbook: Guestbook = guestbook

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private val question: Question = question
}
