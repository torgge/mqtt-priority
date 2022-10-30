package org.bonespirito.prioritymq.application

import org.bonespirito.prioritymq.domain.message.MessageProducer
import org.bonespirito.prioritymq.domain.model.Order
import org.bonespirito.prioritymq.domain.model.toPayload
import org.bonespirito.prioritymq.domain.service.OrderFacade
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderFacadeImp(
    @Autowired private val messageProducer: MessageProducer
) : OrderFacade {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun process(order: Order, properties: Map<String, Any>) {
        log.info("\nTrying to process an order $order\n")
        val payload = order.toPayload()
        log.info("Trying to produce a message from payload $payload")
        messageProducer.produce(payload, properties)
    }
}
