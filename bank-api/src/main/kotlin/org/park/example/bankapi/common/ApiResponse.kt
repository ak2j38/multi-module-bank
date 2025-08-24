package org.park.example.bankapi.common

import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
  val success: Boolean,
  val message: String? = null,
  val data: T? = null,
  val error: Error? = null
) {
  companion object {
    fun <T> success(data: T, message: String? = null): ResponseEntity<ApiResponse<T>> {
      return ResponseEntity.ok(ApiResponse(success = true, message = message, data = data))
    }

    fun <T> error(
      message: String,
      errorCode: String? = null,
      details: String? = null,
    ): ResponseEntity<ApiResponse<T>> {
      return ResponseEntity.badRequest().body(
        ApiResponse(false, message, null, Error(errorCode ?: "", details ?: ""))
      )
    }

    fun <T> exceptionError(
      message: String,
      errorCode: String? = null,
      details: String? = null,
    ): ApiResponse<T> {
      return ApiResponse(false, message, null, Error(errorCode ?: "", details ?: ""))
    }
  }
}

data class Error(
  val code: String,
  val details: String,
)
