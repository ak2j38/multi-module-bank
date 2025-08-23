package org.park.bankevent.listener

import org.park.example.bankdomain.event.AccountCreatedEvent
import org.park.example.bankdomain.event.TransactionCreatedEvent
import org.park.example.bankdomain.repository.AccountReadViewRepository
import org.park.example.bankdomain.repository.AccountRepository
import org.park.example.bankdomain.repository.TransactionReadViewRepository
import org.park.example.bankdomain.repository.TransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EventConsumer(
  private val accountRepository: AccountRepository,
  private val accountReadViewRepository: AccountReadViewRepository,
  private val transactionRepository: TransactionRepository,
  private val transactionReadViewRepository: TransactionReadViewRepository
) {
  private val logger = LoggerFactory.getLogger(EventConsumer::class.java)

  @Async("taskExecutor")
  @Retryable(
    value = [Exception::class],
    maxAttempts = 3,
    backoff = Backoff(delay = 1000)
  )
  // 동작 설명
  // api main -> pulish(taskExecutor) -> RetryProxy -> Method -> RetryProxy -> Method
  fun handleAccountCreated(event: AccountCreatedEvent) {

  }

  @Async("taskExecutor")
  fun handleTransactionCreated(event: TransactionCreatedEvent) {

  }
}
