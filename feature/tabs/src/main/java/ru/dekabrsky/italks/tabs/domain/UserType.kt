package ru.dekabrsky.italks.tabs.domain

import java.io.Serializable

enum class UserType(val desc: String): Serializable {
    CHILD("Ребенок"),
    PATIENT("Пациент"),
    PARENT("Законный представитель"),
    DOCTOR("Доктор");

    companion object {
        fun valueByName(string: String?): UserType = values().find { it.name == string } ?: CHILD
    }
}