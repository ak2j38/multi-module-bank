package org.park.example.bankdomain.exception

abstract class BusinessException(
  message: String?, cause: Throwable? = null
): RuntimeException(message, cause) {

}

class AccountNotFoundException(accountNumber: String): BusinessException("Account $accountNumber not found")
