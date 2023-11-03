package com.freewill.enums

enum class DomainType {
    PROFILE,
    CAFE;

    fun toLowerCase(): String = this.toString().lowercase()
}
