package org.park.example.bankcore.exception

abstract class BusinessException(
  message: String?, cause: Throwable? = null
): RuntimeException(message, cause) {

}

class AccountNotFoundException(accountNumber: String): BusinessException("Account $accountNumber not found")
