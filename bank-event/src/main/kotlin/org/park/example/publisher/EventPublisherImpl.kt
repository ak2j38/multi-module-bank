package org.park.example.publisher

import com.sun.org.slf4j.internal.LoggerFactory
import org.park.example.bankdomain.event.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
open class EventPublisherImpl(
  private val eventPublisher: ApplicationEventPublisher
) : EventPublisher {
  private val logger = LoggerFactory.getLogger(EventPublisherImpl::class.java)

  override fun publish(event: DomainEvent) {
    logger.debug("Publishing event: ${event.eventId} at ${event.occurredOn}")
    eventPublisher.publishEvent(event)
  }

  @Async("taskExecutor")
  override fun publishAsync(event: DomainEvent) {
    logger.debug("Publishing event: ${event.eventId} at ${event.occurredOn}")
    eventPublisher.publishEvent(event)
  }

  override fun publishAll(events: List<DomainEvent>) {
    events.forEach { event ->
      logger.debug("Publishing event: ${event.eventId} at ${event.occurredOn}")
      eventPublisher.publishEvent(event)
    }
  }

  @Async("taskExecutor")
  override fun publishAllAsync(events: List<DomainEvent>) {
    events.forEach { event ->
      logger.debug("Publishing event: ${event.eventId} at ${event.occurredOn}")
      eventPublisher.publishEvent(event)
    }
  }
}
