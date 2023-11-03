package com.freewill.enums

enum class ReturnCode(val code: String, val message: String) {
    // Token
    NOT_EXIST_BEARER_SUFFIX("1000", "토큰 앞에 Bearer을 붙여주세요"),

    // User
    WRONG_PROVIDER("1100", "잘못된 제공자입니다."),
    DUPLICATE_USER("1101", "이미 가입된 유저입니다.")
}
