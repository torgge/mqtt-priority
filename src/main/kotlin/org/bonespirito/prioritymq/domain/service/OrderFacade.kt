package org.bonespirito.prioritymq.domain.service

import org.bonespirito.prioritymq.domain.model.Order

interface OrderFacade {
    fun process(order: Order, properties: Map<String, Any>)
}
