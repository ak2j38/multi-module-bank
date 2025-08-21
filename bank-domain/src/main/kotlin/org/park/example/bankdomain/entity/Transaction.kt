package org.park.example.bankdomain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
class Transaction(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  val account: Account,

  @Column(name = "amount", nullable = false)
  val amount: BigDecimal,

  @Column(name = "transaction_type", nullable = false)
  @Enumerated(value = EnumType.STRING)
  val transactionType: TransactionType,

  @Column(name = "description", nullable = true)
  val description: String? = null,

  @Column(name = "created_at", nullable = false, updatable = false)
  val createdAt: LocalDateTime = LocalDateTime.now(),
)

enum class TransactionType {
  DEPOSIT,
  WITHDRAWAL,
  TRANSFER
}
