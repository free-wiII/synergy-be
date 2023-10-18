package com.freewill.common.response

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T?
) {
    companion object {
        private val successStatus: String = "SUCCESS"
        private val failStatus: String = "FAIL"

        fun <T> createSuccessWithData(message: String, data: T? = null): ApiResponse<T> {
            return ApiResponse<T>(successStatus, message, data)
        }

        fun <T> createSuccess(message: String): ApiResponse<T> {
            return createSuccessWithData(message)
        }

        fun <T> createFailWithData(message: String, data: T? = null): ApiResponse<T> {
            return ApiResponse<T>(failStatus, message, data)
        }

        fun <T> createFail(message: String): ApiResponse<T> {
            return createFailWithData(message)
        }
    }
}
