package ru.dekabrsky.easylife.activity.provider

import ru.dekabrsky.easylife.activity.view.MainActivity
import ru.dekabrsky.easylife.basic.di.AppActivityProvider
import javax.inject.Inject
import javax.inject.Provider

class AppActivityProviderImpl @Inject constructor() : Provider<Class<*>>, AppActivityProvider {
    override fun get(): Class<*> = MainActivity::class.java
}