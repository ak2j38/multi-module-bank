package org.park.example.bankcore.lock

import org.park.example.bankcore.exception.LockAcquisitionException
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@ConfigurationProperties(prefix = "bank.lock")
data class DistributionLock(
  val timeout: Long = 5000,
  val leaseTime: Long = 10000,
  val retryInterval: Long = 5000,
  val maxRetryAttemps: Long = 50
)

@Service
@EnableConfigurationProperties(DistributionLock::class)
class DistributionLockService(
  private val redissonClient: RedissonClient,
  private val lockProperties: DistributionLock
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun <T> executeWithLock(
    lockKey: String,
    action: () -> T
  ): T {
    val lock = redissonClient.getLock(lockKey)
    return try {
      val acquired = lock.tryLock(
        lockProperties.timeout,
        lockProperties.leaseTime,
        TimeUnit.MILLISECONDS
      )

      if (!acquired) {
        logger.error("Lock [$lockKey] failed to acquire lock")
        throw LockAcquisitionException("Acquring lock for $lockKey")
      }

      try {
        action()
      } finally {
        if (lock.isHeldByCurrentThread) {
          lock.unlock()
        }
      }
    } catch (e: Exception) {
      logger.error("Failed to acquire lock for key: $lockKey", e)
      throw e
    }
  }

  fun <T> executeWithAccountLock(
    accountNumber: String,
    action: () -> T
  ): T {
    val lockKey = "account:lock:$accountNumber"
    return executeWithLock(lockKey, action)
  }

  fun <T> executeWithTransactionLock(
    from: String,
    to: String,
    action: () -> T
  ): T {
    val sorted = listOf(from, to).sorted()
    val lockKey = "transaction:lock:${sorted[0]}:${sorted[1]}"

    return executeWithLock(lockKey, action)
  }
}
