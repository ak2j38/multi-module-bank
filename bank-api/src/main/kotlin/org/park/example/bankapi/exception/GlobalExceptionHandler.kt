package org.park.example.bankapi.exception

import org.park.example.bankapi.common.ApiResponse
import org.park.example.bankcore.exception.AccountNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {
  private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)


  @ExceptionHandler(AccountNotFoundException::class)
  fun handleAccountNotFoundException(ex: AccountNotFoundException): ResponseEntity<ApiResponse> {
    logger.error("Account not found: ${ex.message}", ex)

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ApiResponse.failure(
        errorCode = "ACCOUNT_NOT_FOUND",
        details = ex.message ?: "Account not found",
        message = "The requested account could not be found."
      )
    )
  }

  private fun getPath(request: WebRequest): String? {
    return request.getDescription(false)
      .removePrefix("uri=")
      .takeIf { it.isNotBlank() }
  }
}
