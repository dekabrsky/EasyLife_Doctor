package ru.dekabrsky.callersbase.data.model

import androidx.annotation.Keep

@Keep
class MessageRequest(
    val chatId: Long,
    val text: String
)