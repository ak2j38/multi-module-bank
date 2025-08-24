package org.park.example.bankapi.service

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.park.example.bankapi.common.ApiResponse
import org.park.example.bankcore.common.TxAdvice
import org.park.example.bankdomain.entity.AccountReadView
import org.park.example.bankdomain.repository.AccountReadViewRepository
import org.park.example.bankdomain.repository.TransactionReadViewRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AccountReadService(
  private val txAdvice: TxAdvice,
  private val accountReadViewRepository: AccountReadViewRepository,
  private val transactionReadViewRepository: TransactionReadViewRepository,
  private val circuitBreaker: CircuitBreakerRegistry
) {
  private val logger = LoggerFactory.getLogger(AccountReadService::class.java)
  private val breaker = circuitBreaker.circuitBreaker("accountReadView")

  fun getAccount(accountNumber: String): ResponseEntity<ApiResponse<AccountReadView>> {
    return breaker.execute(
      operation = {
        txAdvice.readOnly {
          val response = accountReadViewRepository.findByAccountNumber(accountNumber)

          return if (response.isEmpty) {
            ApiResponse.error("Account not found", "ACCOUNT_NOT_FOUND", "The requested account could not be found.")
          } else {
            ApiResponse.success(response)
          }
        }
      },
      fallback = { exception ->
        logger.warn("Get account failed", exception)
        ApiResponse.error(
          errorCode = "SERVICE_UNAVAILABLE",
          details = exception.message ?: "Service is currently unavailable",
          message = "Please try again later."
        )
      }
    )
  }
}
