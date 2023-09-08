package com.freewill.security.oauth.client

import com.freewill.security.oauth.key.OidcPublicKeyResponse

interface AuthClient {
    fun getPublicKeys(): OidcPublicKeyResponse
}
