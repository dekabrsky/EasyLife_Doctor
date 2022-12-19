package ru.dekabrsky.italks.tabs.domain

enum class UserType(val desc: String) {
    CHILD("Ребенок"),
    PATIENT("Пациент"),
    PARENT("Законный представитель"),
    DOCTOR("Доктор");

    companion object {
        fun valueByName(string: String?): UserType = values().find { it.name == string } ?: CHILD
    }
}