package com.freewill.domain.cafe.repository

import com.freewill.domain.cafe.entity.Cafe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CafeRepository : JpaRepository<Cafe, Long>
