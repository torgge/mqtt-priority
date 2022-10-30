package org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq.configurations.out")
class PriorityQueueProperties : RabbitMqProperties {
    override lateinit var routingKey: String
    override lateinit var queue: String
    override lateinit var exchange: String
    override lateinit var dlqQueue: String
    override lateinit var dlqExchange: String
    override lateinit var dlqRoutingKey: String
    override var xMaxPriority: Int = 0
}
