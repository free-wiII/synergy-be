package com.freewill.security.oauth.client

import com.freewill.security.oauth.dto.key.OidcPublicKeyResponse
import com.freewill.security.oauth.properties.AppleProperties
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AppleAuthClient(
    private val webClientBuilder: WebClient.Builder,
    private val appleProperties: AppleProperties
) : AuthClient {
    override fun getPublicKeys(): OidcPublicKeyResponse {
        return webClientBuilder
            .baseUrl(appleProperties.publicKeyUrl)
            .build()
            .get()
            .retrieve()
            .bodyToMono(OidcPublicKeyResponse::class.java)
            .block()!!
    }
}
