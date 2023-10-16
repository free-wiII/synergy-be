package com.freewill.repository.jpa

import com.freewill.entity.Guestbook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GuestbookRepository : JpaRepository<Guestbook, Long>
