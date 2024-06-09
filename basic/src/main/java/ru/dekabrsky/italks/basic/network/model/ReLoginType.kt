package ru.dekabrsky.easylife.basic.network.model

import java.io.Serializable

enum class ReLoginType: Serializable {
    PASSWORD,
    PIN,
    DEFAULT
}