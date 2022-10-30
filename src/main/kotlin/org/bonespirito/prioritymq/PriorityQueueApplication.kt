package org.bonespirito.prioritymq

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PriorityQueueApplication

private val log = LoggerFactory.getLogger(PriorityQueueApplication::class.java)
fun main(args: Array<String>) {
    log.info("\n ### STARTING APPLICATION ###")
    runApplication<PriorityQueueApplication>(*args)
    log.info("\n ### APPLICATION WAS STARTED ###")
}
