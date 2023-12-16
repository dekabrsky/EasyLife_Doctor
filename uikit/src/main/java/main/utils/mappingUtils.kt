package main.utils

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0

fun Boolean?.isTrue(): Boolean = this ?: false

fun String.isBlankOrEmpty() = this.isBlank() || this.isEmpty()