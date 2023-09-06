package com.freewill.security.oauth.dto.key

import java.util.function.Supplier
import javax.naming.AuthenticationException

data class OidcPublicKeyResponse(val keys: List<OidcPublicKey>) {
    @Throws(AuthenticationException::class)
    fun getMatchedKey(kid: String?, alg: String?): OidcPublicKey {
        return keys.stream()
            .filter { key: OidcPublicKey ->
                key.kid == kid && key.alg == alg
            }
            .findAny()
            .orElseThrow(Supplier<AuthenticationException> { AuthenticationException() })
    }
}
