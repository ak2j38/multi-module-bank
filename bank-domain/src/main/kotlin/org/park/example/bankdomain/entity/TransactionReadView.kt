package org.park.example.bankdomain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transaction_read_views")
data class TransactionReadView(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "account_id", nullable = false)
  val accountId: Long = 0,

  @Column(name = "account_number", nullable = false)
  val accountNumber: String = "",

  @Column(name = "account_holder_name", nullable = false)
  val accountHolderName: String = "",

  @Column(name = "transaction_type", nullable = false)
  @Enumerated(EnumType.STRING)
  val transactionType: TransactionType = TransactionType.DEPOSIT,

  @Column(name = "amount", nullable = false, precision = 19, scale = 2)
  val amount: BigDecimal = BigDecimal.ZERO,

  @Column(name = "description", nullable = false)
  val description: String = "",

  @Column(name = "created_at", nullable = false, updatable = false)
  val createdAt: LocalDateTime = LocalDateTime.now(),

  @Column(name = "balance_after", nullable = false, precision = 19, scale = 2)
  val balanceAfter: BigDecimal = BigDecimal.ZERO
)
