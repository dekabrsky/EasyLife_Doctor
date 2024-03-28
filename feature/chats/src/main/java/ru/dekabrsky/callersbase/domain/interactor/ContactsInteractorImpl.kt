package ru.dekabrsky.callersbase.domain.interactor

import io.reactivex.Single
import ru.dekabrsky.callersbase.data.repository.ContactsRepository
import ru.dekabrsky.common.domain.interactor.ContactsInteractor
import ru.dekabrsky.common.domain.model.ContactEntity
import javax.inject.Inject

class ContactsInteractorImpl @Inject constructor(
    private val repository: ContactsRepository
): ContactsInteractor {
    override fun getCallersBases(direction: String, sortBy: String) =
        repository.getCallersBases(direction, sortBy)

    override fun getCallersBase(id: Long) = repository.getCallersBase(id)

    fun getChats() = repository.getChats()

    fun getDoctors() = repository.getDoctors()

    fun getPatients() = repository.getPatients()

    override fun getChildren() = repository.getChildren()

    override fun getParents() = repository.getParents()

    fun startChat(id: Long) = repository.startChat(id)

    fun getChat(id: Long) = repository.getChat(id)

    fun observeMessagesWs(chatId: Long) = repository.observeMessagesWs(chatId)

    fun postMessageWs(chatId: Long, msg: String) = repository.postMessageWs(chatId, msg)
}