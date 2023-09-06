package com.freewill.security.oauth.client

import com.freewill.security.oauth.dto.key.OidcPublicKeyResponse

interface AuthClient {
    fun getPublicKeys(): OidcPublicKeyResponse;
}