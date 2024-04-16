package ru.dekabrsky.italks.basic.network.model

import java.io.Serializable

enum class ReLoginType: Serializable {
    PASSWORD,
    PIN,
    DEFAULT
}