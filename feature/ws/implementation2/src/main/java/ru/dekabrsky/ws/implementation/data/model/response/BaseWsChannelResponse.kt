package ru.dekabrsky.ws.implementation2.data.response

import androidx.annotation.Keep
import ru.dekabrsky.ws.implementation2.data.model.ChannelType

@Keep
class BaseWsChannelResponse(
    val target: ChannelType,
    val type: Long
)