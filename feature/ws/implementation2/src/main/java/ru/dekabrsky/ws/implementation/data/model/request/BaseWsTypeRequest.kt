package ru.dekabrsky.ws.implementation2.data.model.request

import java.io.Serializable

class BaseWsTypeRequest(val type: Long) : Serializable

class ConnectRequest(
    val protocol: String = "json",
    val version: Int = 1
) : Serializable

// {"arguments":[{"chatId": 1, "text": "Хеллоу"}],"target":"SendMessage","type":1}
class SendMessageRequest(
    val arguments: List<Any>,
    val target: String,
    val type: Int = 1
) : Serializable