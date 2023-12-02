package main.utils

fun Int?.orZero(): Int = this ?: 0

fun Boolean?.isTrue(): Boolean = this ?: false