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
@Table(name = "accounts")
class Account(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "account_number", nullable = false, unique = true)
  val accountNumber: String,

  @Column(name = "balance", nullable = false)
  var balance: BigDecimal,

  @Column(name = "accout_holder_name", nullable = false)
  val accountHolderName: String,

  @Column(name = "created_at", nullable = false, updatable = false)
  val createdAt: LocalDateTime = LocalDateTime.now(),

  @Column(name = "last_updated_at", nullable = false)
  var lastUpdatedAt: LocalDateTime = LocalDateTime.now()
)
