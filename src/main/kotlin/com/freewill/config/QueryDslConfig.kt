package com.freewill.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDslConfig {

    @PersistenceContext
    lateinit var em: EntityManager

    @Bean
    public fun queryFactory(): JPAQueryFactory = JPAQueryFactory(em)
}
