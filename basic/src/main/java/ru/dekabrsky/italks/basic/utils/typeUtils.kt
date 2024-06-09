package ru.dekabrsky.easylife.basic.utils

inline fun <reified T : Enum<T>> String.asEnumOrDefault(defaultValue: T? = null): T? =
    enumValues<T>().firstOrNull { it.name.equals(this, ignoreCase = true) } ?: defaultValue

inline fun <reified T : Enum<T>> String.asEnumOrDefaultNonNull(defaultValue: T): T =
    this.asEnumOrDefault(defaultValue) ?: defaultValue