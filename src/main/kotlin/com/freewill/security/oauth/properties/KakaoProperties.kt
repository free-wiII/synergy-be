package com.freewill.security.oauth.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KakaoProperties {
    @Value("\${oauth2.kakao.public-key-info}")
    lateinit var publicKeyUrl: String
}
