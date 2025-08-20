package org.park.example.bankapi.common

data class ApiResponse(
  val success: Boolean,
  val message: String? = null,
  val data: Any? = null,
  val error: Error? = null
) {
  companion object {
    fun <T> success(data: T, message: String? = null): ApiResponse {
      return ApiResponse(success = true, message = message, data = data)
    }

    fun failure(errorCode: String, details: String, message: String? = null): ApiResponse {
      return ApiResponse(success = false, message = message, error = Error(code = errorCode, details = details))
    }
  }
}

data class Error(
  val code: String,
  val details: String,
)
