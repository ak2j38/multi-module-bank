package org.park.example.bankcore.common

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class CircuitBreakerConfig {

  @Bean
  fun circuitBreakerRegistry(): CircuitBreakerRegistry {
    val config = CircuitBreakerConfig.custom()
      .failureRateThreshold(50f)
      .waitDurationInOpenState(Duration.ofMillis(30))
      .permittedNumberOfCallsInHalfOpenState(3)
      .slidingWindowSize(5)
      .minimumNumberOfCalls(3)
      .build()

    return CircuitBreakerRegistry.of(config)
  }
}

object CircuitBreakerUtils {

  fun <T> CircuitBreaker.execute(
    operation: () -> T,
    fallback: (Exception) -> T
  ): T {
    return try {
      val supplier = CircuitBreaker.decorateSupplier(this) { operation() }
      supplier.get()
    } catch (e: Exception) {
      fallback(e)
    }
  }
}
