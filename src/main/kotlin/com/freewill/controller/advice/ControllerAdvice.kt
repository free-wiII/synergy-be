package com.freewill.controller.advice

import com.freewill.common.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(exception.message?.let { ApiResponse.createFail(it) })
    }
}