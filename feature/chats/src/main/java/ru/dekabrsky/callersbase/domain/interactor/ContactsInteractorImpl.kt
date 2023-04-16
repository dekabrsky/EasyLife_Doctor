package ru.dekabrsky.callersbase.domain.interactor

import ru.dekabrsky.callersbase.data.repository.ContactsRepository
import ru.dekabrsky.common.domain.interactor.ContactsInteractor
import javax.inject.Inject

class ContactsInteractorImpl @Inject constructor(
    private val repository: ContactsRepository
): ContactsInteractor {
    override fun getCallersBases(direction: String, sortBy: String) =
        repository.getCallersBases(direction, sortBy)

    override fun getCallersBase(id: Int) = repository.getCallersBase(id)

    fun getChats() = repository.getChats()
}