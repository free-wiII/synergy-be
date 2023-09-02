package com.freewill.domain.question.repository

import com.freewill.domain.question.entity.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long>
