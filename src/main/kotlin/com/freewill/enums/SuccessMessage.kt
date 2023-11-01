package com.freewill.enums

enum class SuccessMessage(val msg: String) {
    SUCCESS_SIGN_UP("회원 가입 성공"),
    SUCCESS_SIGN_IN("로그인 성공"),
    SUCCESS_SEARCH_PROFILE("프로필 조회 성공"),
    SUCCESS_UPDATE_PROFILE("프로필 수정 성공"),
    SUCCESS_REGISTER_CAFE("카페 등록 성공"),
    SUCCESS_SEARCH_CAFE("카페 단일 조회 성공"),
    SUCCESS_REGISTER_GUESTBOOK("방명록 등록 성공"),
    SUCCESS_SEARCH_GUESTBOOKS("방명록 리스트 조회 성공"),
    SUCCESS_UPDATE_RECOMMENDATION("추천 or 추천 취소 성공")
}
