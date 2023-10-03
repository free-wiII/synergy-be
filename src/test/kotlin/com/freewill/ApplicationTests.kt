package com.freewill

import com.freewill.s3.config.MockS3Config
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(MockS3Config::class)
@SpringBootTest
class ApplicationTests {
    @Test
    fun contextLoads() {
    }
}
