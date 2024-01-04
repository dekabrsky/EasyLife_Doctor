package ru.dekabrsky.feature.loginCommon.domain.model

import java.io.Serializable

enum class UserType(val serverId: Int, val desc: String): Serializable {
    CHILD(1, "Ребенок"),
    PATIENT(2, "Пациент"),
    PARENT(3, "Законный представитель"),
    DOCTOR(4, "Доктор");

    companion object {
        fun valueByName(string: String?): UserType = values().find { it.name == string } ?: CHILD

        fun valueByServerId(id: Int?): UserType = values().find { it.serverId == id } ?: CHILD
    }
}