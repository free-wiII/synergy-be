package com.freewill.controller.advice

import com.freewill.common.exception.AlreadyRegisterUserException
import com.freewill.common.response.ApiResponse
import com.freewill.enums.FailMessage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["com.freewill.controller"])
class AuthControllerAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyRegisterUserException::class)
    fun alreadyRegisterExHandler(exception: AlreadyRegisterUserException): ApiResponse<Void> {
        return ApiResponse.createFail(FailMessage.FAIL_ALREADY_EXIST_USER.msg)
    }
}
