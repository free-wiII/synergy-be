package com.freewill.entity

import jakarta.persistence.*

@Entity
@Table(name = "bookmark")
class Bookmark(
    cafe: Cafe,
    bookmarkGroup: BookmarkGroup
) {
    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "")
    private var flag: Boolean = false

    @ManyToOne
    @JoinColumn(name = "cafe_id", referencedColumnName = "cafe_id")
    val cafe: Cafe = cafe

    @ManyToOne
    @JoinColumn(name = "bookmark_group_id", referencedColumnName = "bookmark_group_id")
    val bookmarkGroup: BookmarkGroup = bookmarkGroup

    fun updateFlag() {
        flag = !flag
    }
}
