package org.bonespirito.prioritymq.domain.enums

enum class Priority(val value: Int) {
    HIGH(3),
    MEDIUM(2),
    LOW(1)
}

fun Priority.getEnumByValue(value: Int) =
    when (value) {
        1 -> Priority.LOW
        2 -> Priority.MEDIUM
        else -> Priority.HIGH
    }
