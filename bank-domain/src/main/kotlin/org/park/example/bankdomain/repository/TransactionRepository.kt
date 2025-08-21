package org.park.example.bankdomain.repository

import org.park.example.bankdomain.entity.Account
import org.park.example.bankdomain.entity.Transaction
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long> {

  fun findByAccountOrderByCreatedAtDesc(account: Account): List<Transaction>

  fun findTopByAccountOrderByCreatedAtDesc(account: Account, pageable: Pageable): List<Transaction>
}
