package org.bonespirito.prioritymq.domain.message

import com.rabbitmq.client.Channel
import org.springframework.amqp.core.Message

interface MessageConsumer {
    fun consume(message: Message, channel: Channel)
}
