package org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.producer

import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.properties.PriorityQueueProperties
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderMessageProducer(
    @Autowired private val rabbitTemplate: RabbitTemplate,
    private val priorityQueueProperties: PriorityQueueProperties
) : GenericProducer(
    rabbitTemplate,
    priorityQueueProperties,
    LoggerFactory.getLogger(OrderMessageProducer::class.java)
)
