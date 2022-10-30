package org.bonespirito.prioritymq.infrastructure.resource

import org.bonespirito.prioritymq.application.ConsumerService
import org.bonespirito.prioritymq.domain.enums.Priority
import org.bonespirito.prioritymq.domain.model.Order
import org.bonespirito.prioritymq.domain.model.toPayload
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.producer.OrderMessageProducer
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils.X_PRIORITY
import org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils.X_RETRIES
import org.bonespirito.prioritymq.infrastructure.resource.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders", produces = [MediaType.APPLICATION_JSON_VALUE])
class OrderResource(
    @Autowired private val messageService: OrderMessageProducer,
    @Autowired private val consumerService: ConsumerService
) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        value = [POST_MAPPING_QUEUE_HIGH]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun postMessageHigh(
        @RequestBody message: Order
    ): HttpEntity<Any?> {
        val props = mapOf(
            X_RETRIES to INITIAL_RETRY_VALUE,
            X_PRIORITY to Priority.HIGH
        )

        messageService.produce(message.toPayload(), props)
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(message)
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        value = [POST_MAPPING_QUEUE_MEDIUM]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun postMessageMedium(
        @RequestBody message: Order
    ): HttpEntity<Any?> {
        val props = mapOf(
            X_RETRIES to INITIAL_RETRY_VALUE,
            X_PRIORITY to Priority.MEDIUM
        )

        messageService.produce(message.toPayload(), props)
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(message)
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        value = [POST_MAPPING_QUEUE_LOW]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun postMessageLow(
        @RequestBody message: Order
    ): HttpEntity<Any?> {
        val props = mapOf(
            X_RETRIES to INITIAL_RETRY_VALUE,
            X_PRIORITY to Priority.LOW
        )

        messageService.produce(message.toPayload(), props)
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(message)
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        value = [POST_MAPPING_START]
    )
    @ResponseStatus(HttpStatus.OK)
    fun startConsume(): HttpEntity<Any?> {
        consumerService.startConsume()
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build()
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        value = [POST_MAPPING_STOP]
    )
    @ResponseStatus(HttpStatus.OK)
    fun stopConsume(): HttpEntity<Any?> {
        consumerService.stopConsume()
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build()
    }
}
