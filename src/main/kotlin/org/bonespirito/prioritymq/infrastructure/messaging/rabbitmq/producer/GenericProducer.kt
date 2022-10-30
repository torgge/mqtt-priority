package org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.producer

import org.bonespirito.prioritymq.domain.enums.Priority
import org.bonespirito.prioritymq.domain.message.MessageProducer
import org.bonespirito.prioritymq.domain.payload.MessagePayload
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.properties.RabbitMqProperties
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils.X_EXECUTE_AFTER
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils.X_PRIORITY
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils.X_RETRIES
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils.getRetryDate
import org.slf4j.Logger
import org.springframework.amqp.rabbit.core.RabbitTemplate

abstract class GenericProducer(
    private val rabbitTemplate: RabbitTemplate,
    private val rabbitMqProperties: RabbitMqProperties,
    private val logger: Logger
) : MessageProducer {

    override fun produce(payload: MessagePayload, properties: Map<String, Any>) {
        logger.info("publishing message: $payload properties $properties")

        val priority = properties[X_PRIORITY] as Priority

        this.rabbitTemplate.convertAndSend(
            rabbitMqProperties.exchange,
            rabbitMqProperties.routingKey,
            payload
        ) { m ->
            m.messageProperties.headers[X_RETRIES] = properties[X_RETRIES]
            m.messageProperties.headers[X_EXECUTE_AFTER] = getRetryDate()
            m.messageProperties.priority = priority.value
            m
        }

        logger.info("### published payload: $payload ###")
    }

    override fun dlqProduce(payload: MessagePayload, properties: Map<String, Any>) {
        logger.info("publishing dlq message: $payload properties: $properties")

        val priority = properties[X_PRIORITY] as Priority

        this.rabbitTemplate.convertAndSend(
            rabbitMqProperties.dlqExchange,
            rabbitMqProperties.dlqRoutingKey,
            payload
        ) { m ->
            m.messageProperties.headers[X_RETRIES] = properties[X_RETRIES]
            m.messageProperties.headers[X_EXECUTE_AFTER] = getRetryDate()
            m.messageProperties.priority = priority.value
            m
        }

        logger.info("### published payload: $payload ###")
    }
}
