package com.freewill.security.oauth.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppleProperties {
    @Value("\${oauth2.apple.public-key-url}")
    lateinit var publicKeyUrl: String
}