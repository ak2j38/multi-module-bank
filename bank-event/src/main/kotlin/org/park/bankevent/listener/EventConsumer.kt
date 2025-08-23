package org.park.bankevent.listener

import org.example.park.metrics.BankMetrics
import org.park.example.bankcore.common.TxAdvice
import org.park.example.bankdomain.entity.AccountReadView
import org.park.example.bankdomain.entity.TransactionReadView
import org.park.example.bankdomain.entity.TransactionType
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
import java.math.BigDecimal
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime

@Component
class EventConsumer(
  private val accountRepository: AccountRepository,
  private val accountReadViewRepository: AccountReadViewRepository,
  private val transactionRepository: TransactionRepository,
  private val transactionReadViewRepository: TransactionReadViewRepository,
  private val bankMetrics: BankMetrics,
  private val txAdvice: TxAdvice
) {
  private val logger = LoggerFactory.getLogger(EventConsumer::class.java)

  @Async("taskExecutor")
  @Retryable(
    value = [Exception::class],
    maxAttempts = 3,
    backoff = Backoff(delay = 1000)
  )
  fun handleAccountCreated(event: AccountCreatedEvent) {
    val startTime = Instant.now()
    val eventType = "AccountCreatedEvent"

    logger.info("event received $eventType")

    try {
      txAdvice.runNew {
        val account = accountRepository.findById(event.accountId).orElseThrow {
          IllegalStateException("Account not found: ${event.accountId}")
        }

        val accountReadView = AccountReadView(
          id = account.id,
          accountNumber = account.accountNumber,
          accountHolderName = account.accountHolderName,
          balance = account.balance,
          createdAt = account.createdAt,
          lastUpdatedAt = LocalDateTime.now(),
          transactionCount = 0,
          totalDeposits = BigDecimal.ZERO,
          totalWithdrawals = BigDecimal.ZERO
        )

        accountReadViewRepository.save(accountReadView)
        logger.info("AccountReadView created for accountId: ${account.id}")
      }

      val duration = Duration.between(startTime, Instant.now())
      bankMetrics.recordEventProcessingTime(duration, eventType)
      bankMetrics.incrementEventProcessed(eventType)
    } catch (e: Exception) {
      logger.error("Error processing $eventType", e)
      bankMetrics.incrementEventFailed(eventType)
      throw e
    }
  }

  @Async("taskExecutor")
  fun handleTransactionCreated(event: TransactionCreatedEvent) {
    val startTime = Instant.now()
    val eventType = "TransactionCreatedEvent"

    logger.info("event received $eventType")

    try {
      txAdvice.runNew {
        val transaction = transactionRepository.findById(event.transactionId).orElseThrow {
          IllegalStateException("Transaction not found: ${event.transactionId}")
        }

        val account = accountRepository.findById(event.accountId).orElseThrow {
          IllegalStateException("Account not found: ${event.accountId}")
        }

        val transactionReadView = TransactionReadView(
          id = transaction.id,
          accountId = event.accountId,
          accountNumber = account.accountNumber,
          transactionType = transaction.transactionType,
          amount = event.amount,
          description = event.description,
          createdAt = transaction.createdAt,
          balanceAfter = event.balanceAfter
        )

        transactionReadViewRepository.save(transactionReadView)
        logger.info("TransactionReadView created for transactionId: ${transaction.id}")

        val accountReadView = accountReadViewRepository.findById(account.id!!).orElseThrow {
          IllegalStateException("AccountReadView not found: ${account.id}")
        }

        val updatedAccountReadView = accountReadView.copy(
          balance = account.balance,
          lastUpdatedAt = LocalDateTime.now(),
          transactionCount = accountReadView.transactionCount + 1,
          totalDeposits = if (event.type == TransactionType.DEPOSIT) {
            accountReadView.totalDeposits + event.amount
          } else {
            accountReadView.totalDeposits
          },
          totalWithdrawals = if (event.type == TransactionType.WITHDRAWAL) {
            accountReadView.totalWithdrawals + event.amount
          } else {
            accountReadView.totalWithdrawals
          }
        )

        accountReadViewRepository.save(updatedAccountReadView)
        logger.info("AccountReadView updated for accountId: ${account.id}")
      }

      val duration = Duration.between(startTime, Instant.now())
      bankMetrics.recordEventProcessingTime(duration, eventType)
      bankMetrics.incrementEventProcessed(eventType)
    } catch (e: Exception) {
      logger.error("Error processing $eventType", e)
      bankMetrics.incrementEventFailed(eventType)
      throw e
    }
  }
}
