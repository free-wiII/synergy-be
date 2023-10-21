package com.freewill.docs

import com.fasterxml.jackson.databind.ObjectMapper
import com.freewill.filter.MockSecurityFilter
import com.freewill.security.jwt.util.JwtValidator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.nio.charset.StandardCharsets

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@Import(RestDocsConfiguration::class)
@AutoConfigureRestDocs
@WebMvcTest
open class RestDocsTest {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var jwtValidator: JwtValidator

    @BeforeEach
    fun setMockMvc(
        context: WebApplicationContext,
        provider: RestDocumentationContextProvider
    ) {
        MockitoAnnotations.openMocks(this)

        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(provider)
                    .uris()
                    .withScheme("http")
                    .withHost("127.0.0.1")
                    .withPort(8080)
            )
            .apply<DefaultMockMvcBuilder>(springSecurity(MockSecurityFilter()))
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(print())
            .alwaysDo<DefaultMockMvcBuilder>(document("api/v1"))
            .build()
    }

    protected fun toJson(value: Any): String {
        return objectMapper.writeValueAsString(value)
    }

    protected fun toMultiPartFile(name: String, part: String): MockMultipartFile {
        return MockMultipartFile(name, "", "application/json", part.toByteArray(StandardCharsets.UTF_8))
    }

    protected fun getMockMultiPartFile(name: String): MockMultipartFile {
        val path1 = "$name.png"
        val contentType1 = "image/png"

        return MockMultipartFile(name, path1, contentType1, "mockMultipartFile".toByteArray())
    }
}
