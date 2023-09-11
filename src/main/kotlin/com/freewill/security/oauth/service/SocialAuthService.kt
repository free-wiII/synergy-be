package com.freewill.security.oauth.service

interface SocialAuthService {
    fun getProviderId(idToken: String): String
}
