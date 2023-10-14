package com.freewill.enums

enum class ResponseMessage(val msg: String) {
    SUCCESS_SIGN_UP("회원 가입 성공"),
    SUCCESS_SIGN_IN("로그인 성공"),
    SUCCESS_SEARCH_PROFILE("프로필 조회 성공"),
    SUCCESS_UPDATE_PROFILE("프로필 수정 성공")
}
