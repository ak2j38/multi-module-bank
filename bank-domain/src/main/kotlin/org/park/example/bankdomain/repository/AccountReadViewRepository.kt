package org.park.example.bankdomain.repository

import org.park.example.bankdomain.entity.AccountReadView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.util.*

interface AccountReadViewRepository : JpaRepository<AccountReadView, Long> {

  fun findByAccountNumber(accountNumber: Long): Optional<AccountReadView>

  @Query(
    """
    SELECT a FROM AccountReadView a
    ORDER BY a.balance DESC
  """
  )
  fun findAllOrderByBalanceDesc(): List<AccountReadView>

  @Query(
    """
    SELECT a FROM AccountReadView a
    WHERE a.balance >= :minBalance
    ORDER BY a.balance DESC
  """
  )
  fun findByBalanceGreaterThanEqual(@Param("minBalance") minBalance: BigDecimal): List<AccountReadView>
}
