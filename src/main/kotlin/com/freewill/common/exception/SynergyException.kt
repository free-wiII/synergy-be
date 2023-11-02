package com.freewill.common.exception

import com.freewill.enums.ReturnCode

class SynergyException(
    returnCode: ReturnCode
) : RuntimeException() {
    private val code: String
    private val msg: String

    init {
        this.code = returnCode.code
        this.msg = returnCode.message
    }

    fun getCode(): String = this.code
    fun getMsg(): String = this.msg
}
