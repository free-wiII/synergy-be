package com.freewill.security.oauth.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import org.springframework.stereotype.Service
import java.util.Objects.isNull
import javax.naming.AuthenticationException

@Service
class GoogleAuthService(
    private val verifier: GoogleIdTokenVerifier
) : SocialAuthService {
    @Throws(AuthenticationException::class)
    override fun getProviderId(idToken: String): String {
        val googleIdToken: GoogleIdToken = verifier.verify(idToken)
        if (isNull(googleIdToken)) {
            throw AuthenticationException()
        }

        return googleIdToken.payload.subject
    }
}
