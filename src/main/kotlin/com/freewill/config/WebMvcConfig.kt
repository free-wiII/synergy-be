package com.freewill.config

import com.freewill.global.common.resolver.AuthorizedUserResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.method.support.HandlerMethodArgumentResolver

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(AuthorizedUserResolver())
    }
}
