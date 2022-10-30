package org.bonespirito.prioritymq.domain.enums

enum class Priority(val value: Int) {
    HIGH(2),
    MEDIUM(1),
    LOW(0)
}

fun Priority.getEnumByValue(value: Int) =
    when (value) {
        0 -> Priority.LOW
        1 -> Priority.MEDIUM
        else -> Priority.HIGH
    }
