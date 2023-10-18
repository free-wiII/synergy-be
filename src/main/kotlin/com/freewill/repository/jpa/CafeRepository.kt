package com.freewill.repository.jpa

import com.freewill.entity.Cafe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CafeRepository : JpaRepository<Cafe, Long>
