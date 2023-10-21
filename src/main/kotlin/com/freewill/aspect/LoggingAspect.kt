package com.freewill.aspect

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.lang.reflect.Method


@Aspect
@Component
class LoggingAspect(
    private val log: Logger = LoggerFactory.getLogger("AOP LOGGER")
) {
    @Pointcut("execution(* com.freewill.controller..*(..))")
    fun cut() {
    }

    @Before(value = "cut()")
    fun beforeParameterLog(joinPoint: JoinPoint) {
        val request: HttpServletRequest =
            (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        log.info("======= request uri = {} =======", request.requestURI);
        log.info("======= http method = {} =======", request.method);

        val method: Method = getMethod(joinPoint)
        log.info("======= method name = {} =======", method.name)

        val args: Array<Any>? = joinPoint.args

        args?.run {
            for (arg in this) {
                log.info("parameter type = {}", arg.javaClass.simpleName)
                log.info("parameter value = {}", arg)
            }
        }
    }

    @AfterReturning(value = "cut()", returning = "returnValue")
    fun afterReturnLog(returnValue: Any?) {
        returnValue?.run {
            log.info("return type = {}", this.javaClass.simpleName)
            log.info("return value = {}", this)
        }
    }

    @AfterThrowing(value = "cut()", throwing = "exception")
    @Throws(RuntimeException::class)
    fun afterThrowingLog(joinPoint: JoinPoint?, exception: Exception) {
        exception?.run {
            log.info("exception type = {}", exception.javaClass.simpleName)
            log.info("message value = {}", exception.message)
        }
    }

    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature: MethodSignature = joinPoint.signature as MethodSignature
        return signature.method
    }
}
