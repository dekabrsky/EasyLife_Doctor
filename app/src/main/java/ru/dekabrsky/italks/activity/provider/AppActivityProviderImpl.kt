package ru.dekabrsky.italks.activity.provider

import ru.dekabrsky.feature.notifications.implementation.provider.AppActivityProvider
import ru.dekabrsky.italks.activity.view.MainActivity
import javax.inject.Inject
import javax.inject.Provider

class AppActivityProviderImpl @Inject constructor() : Provider<Class<*>>, AppActivityProvider {
    override fun get(): Class<*> = MainActivity::class.java
}