package ru.dekabrsky.callersbase.data.ws

import io.reactivex.Observable
import ru.dekabrsky.callersbase.data.model.MessageResponse
import ru.dekabrsky.callersbase.data.model.MessageRequest
import ru.dekabrsky.ws.implementation2.WsService
import ru.dekabrsky.ws.implementation2.data.model.ChannelType
import javax.inject.Inject

class ChatWsService @Inject constructor(
    private val baseWsService: WsService
) {

    fun subscribeMessages(chatId: Int): Observable<MessageResponse> {
        return baseWsService.subscribe<MessageResponse>(
            ChannelType.ReceiveMessage,
            MessageResponse::class.java
        ).filter { it.chatId == chatId }
    }

    fun postMessage(chatId: Int, message: String) {
        return baseWsService.sendRequest(ChannelType.SendMessage, listOf(MessageRequest(chatId, message)))
    }

}