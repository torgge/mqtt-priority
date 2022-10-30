package org.bonespirito.prioritymq.infrastructure.messaging.rabbitmq.utils

import java.util.Date

fun getRetryDate() = Date(System.currentTimeMillis() + NEXT_RETRY_MILLISECONDS)

fun allowProcessMessage(date: Date) = date < Date(System.currentTimeMillis())
