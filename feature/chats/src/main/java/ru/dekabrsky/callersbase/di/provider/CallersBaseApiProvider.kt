package ru.dekabrsky.callersbase.di.provider

import retrofit2.Retrofit
import ru.dekabrsky.callersbase.data.api.ContactsApi
import javax.inject.Inject
import javax.inject.Provider

class CallersBaseApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<ContactsApi> {

    override fun get(): ContactsApi = retrofit.create(ContactsApi::class.java)

}