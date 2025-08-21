package org.park.example.bankdomain.dto

import org.park.example.bankdomain.entity.TransactionType
import java.math.BigDecimal

data class TransactionCommand(
  val accountNumber: String,
  val amount: BigDecimal,
  val transactionType: TransactionType,
  val description: String
)
