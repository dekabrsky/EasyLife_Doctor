package ru.dekabrsky.avatar.domain

enum class AvatarType {
    BEAR,
    CAT,
    DRAGON,
    ELK,
    KORGI,
    OWL,
    PIG,
    PIGEON,
    PUG,
    RABBIT,
    RAM,
    WOLF;

    companion object {
        fun getByValueOrNull(s: String) = values().find { it.name == s }
    }
}