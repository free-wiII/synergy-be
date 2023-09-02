package com.freewill.domain.guestbook.repository

import com.freewill.domain.guestbook.entity.Guestbook
import org.springframework.data.jpa.repository.JpaRepository

interface GuestbookRepository : JpaRepository<Guestbook, Long>
