package com.freewill.domain.region.repository

import com.freewill.domain.region.entity.Region
import org.springframework.data.jpa.repository.JpaRepository

interface RegionRepository : JpaRepository<Region, Long>
