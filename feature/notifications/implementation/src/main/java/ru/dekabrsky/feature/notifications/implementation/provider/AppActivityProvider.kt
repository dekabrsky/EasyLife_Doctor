package ru.dekabrsky.feature.notifications.implementation.provider

interface AppActivityProvider {
    fun get(): Class<*>
}