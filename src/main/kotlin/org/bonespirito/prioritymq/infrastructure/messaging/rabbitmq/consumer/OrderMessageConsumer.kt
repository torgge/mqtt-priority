package org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.consumer

import com.rabbitmq.client.Channel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.bonespirito.prioritymq.domain.message.MessageConsumer
import org.bonespirito.prioritymq.domain.payload.OrderPayloadRequest
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils.CONSUMER_ID
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class OrderMessageConsumer : MessageConsumer {

    private val log = LoggerFactory.getLogger(javaClass)
    private var json = Json { ignoreUnknownKeys = true; isLenient = true }

    @RabbitListener(
        id = CONSUMER_ID,
        queues = ["\${spring.rabbitmq.configurations.out.queue}"],
        autoStartup = "false"
    )
    override fun consume(message: Message, channel: Channel) {
        Thread.sleep(3000L) // This is only a test
        val tag = message.messageProperties.deliveryTag
        val priority = message.messageProperties.priority

        val payload: OrderPayloadRequest = json.decodeFromString(String(message.body))

        log.info(
            "### " + "\n received message from message TAG: $tag " +
                "\n [--> Priority $priority <---]" +
                "\n Message: $payload " + "\n###"
        )
    }
}
