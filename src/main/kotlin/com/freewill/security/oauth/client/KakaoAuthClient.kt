package com.freewill.security.oauth.client

import com.freewill.security.oauth.key.OidcPublicKeyResponse
import com.freewill.security.oauth.properties.KakaoProperties
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoAuthClient(
    private val webClientBuilder: WebClient.Builder,
    private val kakaoProperties: KakaoProperties
) : AuthClient {
    override fun getPublicKeys(): OidcPublicKeyResponse {
        return webClientBuilder
            .baseUrl(kakaoProperties.publicKeyUrl)
            .build()
            .get()
            .retrieve()
            .bodyToMono(OidcPublicKeyResponse::class.java)
            .block()!!
    }
}
