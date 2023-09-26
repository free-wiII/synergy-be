package com.freewill.security.oauth.key

import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import java.util.Base64
import javax.naming.AuthenticationException

@Component
class PublicKeyGenerator {
    @Throws(AuthenticationException::class, NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun generatePublicKey(
        tokenHeaders: Map<String, String>,
        applePublicKeys: OidcPublicKeyResponse
    ): PublicKey {
        val publicKey: OidcPublicKey = applePublicKeys.getMatchedKey(tokenHeaders["kid"], tokenHeaders["alg"])
        return getPublicKey(publicKey)
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun getPublicKey(publicKey: OidcPublicKey): PublicKey {
        val nBytes: ByteArray = Base64.getUrlDecoder().decode(publicKey.n)
        val eBytes: ByteArray = Base64.getUrlDecoder().decode(publicKey.e)
        val publicKeySpec = RSAPublicKeySpec(
            BigInteger(1, nBytes),
            BigInteger(1, eBytes)
        )
        val keyFactory = KeyFactory.getInstance(publicKey.kty)
        return keyFactory.generatePublic(publicKeySpec)
    }
}
