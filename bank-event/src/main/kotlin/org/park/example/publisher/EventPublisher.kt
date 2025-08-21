package org.park.example.publisher

import org.park.example.bankdomain.event.DomainEvent

interface EventPublisher {
  fun publish(event: DomainEvent)
  fun publishAsync(event: DomainEvent)
  fun publishAll(events: List<DomainEvent>)
  fun publishAllAsync(events: List<DomainEvent>)
}
