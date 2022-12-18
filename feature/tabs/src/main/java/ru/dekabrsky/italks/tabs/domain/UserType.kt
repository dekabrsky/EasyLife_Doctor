package ru.dekabrsky.italks.tabs.domain

enum class UserType {
    CHILD,
    PATIENT,
    PARENT,
    DOCTOR;

    companion object {
        fun valueByName(string: String?): UserType = values().find { it.name == string } ?: CHILD
    }
}