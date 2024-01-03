package ru.dekabrsky.ws.implementation2.data.model

import ru.dekabrsky.ws.implementation2.data.response.BaseWsDataResponse

interface WsEventListener<T> {

    val channelType: ChannelType
    val responseType: Class<*>

    val onEvent: (BaseWsDataResponse<T>) -> Unit

}