package org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.properties

interface RabbitMqProperties {
    var routingKey: String
    var queue: String
    var exchange: String
    var dlqQueue: String
    var dlqExchange: String
    var dlqRoutingKey: String
    var xMaxPriority: Int
}
