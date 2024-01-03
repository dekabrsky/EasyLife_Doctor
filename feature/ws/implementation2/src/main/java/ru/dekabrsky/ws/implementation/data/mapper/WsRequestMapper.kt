package ru.dekabrsky.ws.implementation2.data.mapper

import ru.dekabrsky.ws.implementation2.data.model.ChannelType
import ru.dekabrsky.ws.implementation2.data.model.request.ConnectRequest
import ru.dekabrsky.ws.implementation2.data.model.request.SendMessageRequest
import javax.inject.Inject

class WsRequestMapper @Inject constructor() {

    fun mapConnect() = ConnectRequest()

    fun mapContentRequest(channelType: ChannelType, content: List<Any>) =
        SendMessageRequest(content, channelType.name)
}