package org.park.example.bankdomain.repository

import org.park.example.bankdomain.entity.TransactionReadView
import org.park.example.bankdomain.entity.TransactionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.time.LocalDateTime

interface TransactionReadViewRepository : JpaRepository<TransactionReadView, Long> {

  fun findByAccountIdOrderByCreatedAtDesc(accountId: Long): List<TransactionReadView>

  fun findByAccountNumberOrderByCreatedAtDesc(accountNumber: String): List<TransactionReadView>

  fun findByAccountIdAndTransactionTypeOrderByCreatedAtDesc(accountId: Long, transactionType: TransactionType): List<TransactionReadView>

  @Query(
    """
    SELECT t 
    FROM TransactionReadView t 
    WHERE t.accountId = :accountId 
      AND t.createdAt BETWEEN :startDate AND :endDate 
    ORDER BY t.createdAt DESC
  """
  )
  fun findByAccountIdAndDateRange(
    @Param("accountId") accountId: Long,
    @Param("startDate") startDate: LocalDateTime,
    @Param("endDate") endDate: LocalDateTime
  ): List<TransactionReadView>

  @Query(
    """
    SELECT SUM(t.amount) 
    FROM TransactionReadView t 
    WHERE t.accountId = :accountId AND t.transactionType = :transactionType
    """
  )
  fun sumAmountByAccountIdAndTransactionType(
    @Param("accountId") accountId: Long,
    @Param("transactionType") transactionType: TransactionType
  ): BigDecimal?
}
