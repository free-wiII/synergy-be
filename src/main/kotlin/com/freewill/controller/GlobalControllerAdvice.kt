package com.freewill.controller

import com.freewill.common.exception.SynergyException
import com.freewill.common.response.ApiResponse
import io.jsonwebtoken.JwtException
import jakarta.persistence.PersistenceException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.sql.SQLException
import javax.naming.NamingException


@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(SynergyException::class)
    fun exceptionHandler(exception: SynergyException): ApiResponse<String> {
        return ApiResponse.createFailWithData(exception.getMsg(), exception.getCode())
    }

    @ExceptionHandler(
        value = [
            HttpMessageNotReadableException::class,
            MethodArgumentNotValidException::class,
            HttpRequestMethodNotSupportedException::class,
            MissingServletRequestParameterException::class,
            MethodArgumentTypeMismatchException::class,
            JwtException::class,
            NamingException::class,
            PersistenceException::class
        ]
    )
    fun requestExceptionHandler(exception: Exception): ApiResponse<HttpStatus> {
        return ApiResponse.createFailWithData(exception.message ?: "", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(SQLException::class)
    fun sqlExceptionHandler(exception: Exception): ApiResponse<HttpStatus> {
        return ApiResponse.createFailWithData(exception.message ?: "", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
