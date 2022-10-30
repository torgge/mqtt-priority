package org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.config

import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.properties.PriorityQueueProperties
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
class QueueConfig(
    private val rabbitMqProperties: PriorityQueueProperties
) {
    @Bean
    fun queue(): Queue =
        QueueBuilder
            .durable(rabbitMqProperties.queue)
            .withArgument("x-dead-letter-exchange", rabbitMqProperties.dlqExchange)
            .withArgument("x-dead-letter-routing-key", rabbitMqProperties.dlqRoutingKey)
            .withArgument("x-max-priority", rabbitMqProperties.xMaxPriority)
            .deadLetterExchange(rabbitMqProperties.dlqExchange)
            .deadLetterRoutingKey(rabbitMqProperties.dlqRoutingKey)
            .build()

    @Bean
    fun dlqQueue(): Queue =
        QueueBuilder
            .durable(rabbitMqProperties.dlqQueue)
            .build()

    @Bean
    fun exchange(): DirectExchange =
        ExchangeBuilder
            .directExchange(rabbitMqProperties.exchange)
            .durable(true)
            .build()

    @Bean
    fun dlqExchange(): DirectExchange =
        ExchangeBuilder
            .directExchange(rabbitMqProperties.dlqExchange)
            .durable(true)
            .build()

    @Bean
    fun binding(): Binding? = BindingBuilder
        .bind(queue())
        .to(exchange())
        .with(rabbitMqProperties.routingKey)

    @Bean
    fun dlqBinding(): Binding? = BindingBuilder
        .bind(dlqQueue())
        .to(dlqExchange())
        .with(rabbitMqProperties.dlqRoutingKey)
}

