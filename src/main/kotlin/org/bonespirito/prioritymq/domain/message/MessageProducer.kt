package org.bonespirito.prioritymq.domain.message

import org.bonespirito.prioritymq.domain.payload.MessagePayload

interface MessageProducer {
    fun produce(payload: MessagePayload, properties: Map<String, Any>)
    fun dlqProduce(payload: MessagePayload, properties: Map<String, Any>)
}
