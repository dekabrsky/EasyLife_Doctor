package ru.dekabrsky.common.domain.model

enum class DialingStatus (val value: String) {
    RUN("Запущен"),
    SCHEDULED("Запланирован"),
    DONE("Завершен")

    // todo заменить на строковые ресурсы
}