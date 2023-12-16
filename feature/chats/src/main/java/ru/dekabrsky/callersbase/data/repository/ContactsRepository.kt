package ru.dekabrsky.callersbase.data.repository

import io.reactivex.Observable
import ru.dekabrsky.callersbase.data.api.ContactsApi
import ru.dekabrsky.callersbase.data.mapper.ChatsResponseToEntityMapper
import ru.dekabrsky.callersbase.data.model.MessageRequest
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val api: ContactsApi,
    private val mapper: ChatsResponseToEntityMapper
){
    fun getCallersBases(direction: String, sortBy: String): Observable<List<CallersBaseEntity>> =
        api.getCallersBases(
            direction = direction,
            sortBy = sortBy
        ).map { mapper.mapList(it) }

    fun getCallersBase(id: Int): Observable<CallersBaseEntity> =
        api.getCallersBase(id).map { mapper.mapBase(it) }

    fun getChats() = api.getChats().map { list -> list.chats.map { mapper.mapChat(it) } }

    fun getDoctors() = api.getDoctors().map { mapper.mapUserForChat(it) }.onErrorReturn { emptyList() }

    fun getPatients() = api.getPatients().map { mapper.mapUserForChat(it) }.onErrorReturn { emptyList() }

    fun getChildren() = api.getChildren().map { mapper.mapUserForChat(it) }.onErrorReturn { emptyList() }

    fun startChat(id: Int) = api.startChat(id)

    fun getChat(id: Int) = api.getChat(id).map { mapper.mapChat(it) }

    fun postMessage(chatId: Int, msg: String) = api.postMessage(MessageRequest(chatId, msg))
}