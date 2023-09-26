package com.freewill.security.oauth.util

import com.freewill.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User

class PrincipalUser(
    private val user: User,
    private val attribute: MutableMap<String, Any>,
    private val authorities: MutableCollection<out GrantedAuthority>,
) : UserDetails, OidcUser, OAuth2User {

    override fun getName(): String = user.id.toString()

    override fun getAttributes(): MutableMap<String, Any> = attribute

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun getUsername(): String = user.providerNickname

    override fun getPassword(): String = "password"

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getClaims(): MutableMap<String, Any>? = null

    override fun getUserInfo(): OidcUserInfo? = null

    override fun getIdToken(): OidcIdToken? = null
}