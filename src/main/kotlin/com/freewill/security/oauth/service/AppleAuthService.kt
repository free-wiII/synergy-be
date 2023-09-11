package com.freewill.security.oauth.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.freewill.security.jwt.util.JwtValidator
import com.freewill.security.oauth.client.AppleAuthClient
import com.freewill.security.oauth.util.ApplePublicKeyGenerator
import org.springframework.stereotype.Service
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import javax.naming.AuthenticationException

@Service
class AppleAuthService(
    private val appleAuthClient: AppleAuthClient,
    private val applePublicKeyGenerator: ApplePublicKeyGenerator,
    private val jwtValidator: JwtValidator
) : SocialAuthService {
    @Throws(
        JsonProcessingException::class,
        AuthenticationException::class,
        NoSuchAlgorithmException::class,
        InvalidKeySpecException::class
    )
    override fun getProviderId(idToken: String): String {
        val headers: Map<String, String> = jwtValidator.parseHeaders(idToken)
        val publicKey: PublicKey = applePublicKeyGenerator.generatePublicKey(
            headers,
            appleAuthClient.getPublicKeys()
        )
        return jwtValidator.getTokenClaims(idToken, publicKey)?.subject ?: throw AuthenticationException()
    }
}
