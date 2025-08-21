package org.park.example.bankdomain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "account_read_views")
data class AccountReadView(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "account_number", nullable = false, unique = true)
  val accountNumber: String,

  @Column(name = "accout_holder_name", nullable = false)
  val accountHolderName: String,

  @Column(name = "balance", nullable = false, precision = 19, scale = 2)
  var balance: BigDecimal,

  @Column(name = "created_at", nullable = false, updatable = false)
  val createdAt: LocalDateTime = LocalDateTime.now(),

  @Column(name = "last_updated_at", nullable = false)
  var lastUpdatedAt: LocalDateTime = LocalDateTime.now(),

  @Column(name = "transaction_count", nullable = false)
  var transactionCount: Int = 0,

  @Column(name = "tatal_deposits", nullable = false, precision = 19, scale = 2)
  var totalDeposits: BigDecimal = BigDecimal.ZERO,

  @Column(name = "total_withdrawals", nullable = false, precision = 19, scale = 2)
  var totalWithdrawals: BigDecimal = BigDecimal.ZERO
)
