package com.freewill.domain.review.repository

import com.freewill.domain.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long>
