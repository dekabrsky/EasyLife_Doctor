package ru.dekabrsky.italks.basic.di

import javax.inject.Qualifier

@Qualifier
annotation class ServerEndpoint

@Qualifier
annotation class NotificationDatabaseQualifier

@Qualifier
annotation class WSOkHttpClient