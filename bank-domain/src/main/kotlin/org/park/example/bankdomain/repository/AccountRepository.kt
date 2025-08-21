package org.park.example.bankdomain.repository

import org.park.example.bankdomain.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {

  fun findByAccountNumber(accountNumber: Long): Account?
}
