package com.freewill.domain.question.repository

import com.freewill.domain.question.entity.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long>
