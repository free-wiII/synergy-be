package com.freewill.entity

import jakarta.persistence.*

@Entity
@Table(name = "bookmark_group")
class BookmarkGroup(
    title: String,
    user: User
) {
    @Id
    @Column(name = "bookmark_group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "title", nullable = false)
    private val title: String = title

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    val user: User = user

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookmarkGroup")
    private var bookmarks: MutableList<Bookmark> = mutableListOf()
}
