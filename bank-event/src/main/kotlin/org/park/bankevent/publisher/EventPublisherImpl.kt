package org.park.bankevent.publisher

import org.park.example.bankdomain.event.DomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
open class EventPublisherImpl(
  private val eventPublisher: ApplicationEventPublisher
) : EventPublisher {
  private val logger = LoggerFactory.getLogger(EventPublisherImpl::class.java)

  override fun publish(event: DomainEvent) {
    logger.info("Publishing event: ${event.eventId} at ${event.occurredOn}")
    try {
      eventPublisher.publishEvent(event)
    } catch (e: Exception) {
      logger.error("Failed to publish event: ${event.eventId} at ${event.occurredOn}", e)
      throw e
    }
  }

  @Async("taskExecutor")
  override fun publishAsync(event: DomainEvent) {
    logger.info("Publishing event: ${event.eventId} at ${event.occurredOn}")
    try {
      eventPublisher.publishEvent(event)
    } catch (e: Exception) {
      logger.error("Failed to publish event: ${event.eventId} at ${event.occurredOn}", e)
      throw e
    }
  }

  override fun publishAll(events: List<DomainEvent>) {
    try {
      events.forEach { event ->
        logger.info("Publishing event: ${event.eventId} at ${event.occurredOn}")
        eventPublisher.publishEvent(event)
      }
    } catch (e: Exception) {
      logger.error("Failed to publish events", e)
      throw e
    }
  }

  @Async("taskExecutor")
  override fun publishAllAsync(events: List<DomainEvent>) {
    try {
      events.forEach { event ->
        logger.info("Publishing event: ${event.eventId} at ${event.occurredOn}")
        eventPublisher.publishEvent(event)
      }
    } catch (e: Exception) {
      logger.error("Failed to publish events", e)
      throw e
    }
  }
}
